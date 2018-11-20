package com.xtt.platform.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hslf.usermodel.HSLFShape;
import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hslf.usermodel.HSLFTextParagraph;
import org.apache.poi.hslf.usermodel.HSLFTextRun;
import org.apache.poi.hslf.usermodel.HSLFTextShape;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextRun;
import org.apache.poi.xslf.usermodel.XSLFTextShape;
import org.springframework.web.multipart.MultipartFile;

/**
 * PPT转成成图片
 * 
 * @author dzw
 *
 */
public class PPT2ImageUtil {

    private static Logger log = Logger.getLogger(PPT2ImageUtil.class);

    private static final String PNG = "png";

    public static void main(String[] args) {
        // System.out.println("===========>" + XMLSlideShow.class.getProtectionDomain().getCodeSource().getLocation());
    }

    /**
     * ppt to image
     * 
     * @Title: doPPTtoImage
     * @param pptFile
     *            ppt
     * @param imgFilePath
     *            ppt保存的路径
     * @param userId
     *            用户id
     * @return
     * @throws Exception
     *
     */
    public static List<String> doPPTtoImage(MultipartFile pptFile, String imgFilePath, String returnPath, String filePrefix) throws Exception {
        if (pptFile == null || pptFile.getSize() <= 0) {
            return null;
        }
        List<String> list = new ArrayList<String>();
        // 获取文件后缀
        String imgSuffix = pptFile.getOriginalFilename().substring(pptFile.getOriginalFilename().lastIndexOf("."));
        if (".ppt".equals(imgSuffix.toLowerCase())) {
            list = doPPT2003toImage(pptFile, new File(imgFilePath), returnPath, filePrefix);
        } else if (".pptx".equals(imgSuffix.toLowerCase())) {
            list = doPPT2007toImage(pptFile, new File(imgFilePath), returnPath, filePrefix);
        }
        return list;
    }

    /**
     * 
     * ppt2003 文档的转换 后缀名为.ppt
     * 
     * @param pptFile
     *            ppt文件
     * @param imgFilePath
     *            图片将要保存的目录（不是文件）
     * @param returnPath
     *            要返回的相对路径，没有则返回绝对路径
     * @return 图片存储路径地址
     */
    public static List<String> doPPT2003toImage(MultipartFile pptFile, File imgFilePath, String returnPath) throws Exception {
        return doPPT2003toImage(pptFile, imgFilePath, returnPath, null);
    }

    /**
     * 
     * ppt2003 文档的转换 后缀名为.ppt
     * 
     * @param pptFile
     *            ppt文件
     * @param imgFilePath
     *            图片将要保存的目录（不是文件）
     * @param returnPath
     *            要返回的相对路径，没有则返回绝对路径
     * @param filePrefix
     *            返回文件名的前缀，没有则不加
     * 
     * @return 图片存储路径地址
     */
    public static List<String> doPPT2003toImage(MultipartFile pptFile, File imgFilePath, String returnPath, String filePrefix) throws Exception {
        List<String> list = new ArrayList<String>();
        if (!imgFilePath.exists()) {
            imgFilePath.mkdir();
        }
        try {
            InputStream is = pptFile.getInputStream();
            HSLFSlideShow ppt = new HSLFSlideShow(is);
            // 及时关闭掉 输入流
            is.close();

            // 获取幻灯片
            List<HSLFSlide> slides = ppt.getSlides();
            for (int i = 0; i < slides.size(); i++) {
                Dimension pgsize = ppt.getPageSize();
                // 解决乱码问题
                List<HSLFShape> shapes = slides.get(i).getShapes();
                for (HSLFShape shape : shapes) {
                    if (shape instanceof HSLFTextShape) {
                        HSLFTextShape sh = (HSLFTextShape) shape;
                        List<HSLFTextParagraph> textParagraphs = sh.getTextParagraphs();
                        for (HSLFTextParagraph hslfTextParagraph : textParagraphs) {
                            List<HSLFTextRun> textRuns = hslfTextParagraph.getTextRuns();
                            for (HSLFTextRun hslfTextRun : textRuns) {
                                hslfTextRun.setFontFamily("宋体");
                            }
                        }
                    }
                }
                // 根据幻灯片大小生成图片
                BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
                // 最核心的代码
                slides.get(i).draw(createGraphics(pgsize, img));
                // 图片将要存放的路径
                String nameStr = (filePrefix == null ? "" : (filePrefix + "_")) + UUID.randomUUID().toString();
                String absolutePath = imgFilePath.getAbsolutePath() + "/" + nameStr + "." + PNG;
                File jpegFile = new File(absolutePath);
                // 如果图片存在，则不再生成
                if (jpegFile.exists()) {
                    continue;
                }

                log.info("==>" + jpegFile.getPath());
                list.add(StringUtils.isBlank(returnPath) ? absolutePath : (returnPath + nameStr + "." + PNG));
                // 这里设置图片的存放路径和图片的格式(jpeg,png,bmp等等),注意生成文件路径
                FileOutputStream out = new FileOutputStream(jpegFile);
                // 缩放图片比例
                reSize(pgsize);
                // 写入到图片中去
                ImageIO.write(scale(pgsize, img), PNG, out);
                out.close();
            }
            log.debug("PPT转换成图片 成功！");
        } catch (Exception e) {
            log.error("PPT转换成图片 发生异常！", e);
            throw new Exception("PPT转换成图片 发生异常！");
        }
        return list;
    }

    /**
     * ppt2007文档的转换 后缀为.pptx
     * 
     * @param pptFile
     *            PPT文件
     * @param imgFilePath
     *            图片将要保存的路径目录（不是文件）
     * @param returnPath
     *            要返回的相对路径，没有则返回绝对路径
     * @return 图片存储路径地址
     */
    public static List<String> doPPT2007toImage(MultipartFile pptFile, File imgFilePath, String returnPath) throws Exception {
        return doPPT2007toImage(pptFile, imgFilePath, returnPath, null);
    }

    /**
     * ppt2007文档的转换 后缀为.pptx
     * 
     * @param pptFile
     *            PPT文件
     * @param imgFilePath
     *            图片将要保存的路径目录（不是文件）
     * @param returnPath
     *            要返回的相对路径，没有则返回绝对路径
     * @param filePrefix
     *            返回文件名的前缀，没有则不加
     * @return 图片存储路径地址
     */
    public static List<String> doPPT2007toImage(MultipartFile pptFile, File imgFilePath, String returnPath, String filePrefix) throws Exception {
        List<String> list = new ArrayList<String>();
        if (!imgFilePath.exists()) {
            imgFilePath.mkdir();
        }
        InputStream is = null;
        try {
            is = pptFile.getInputStream();
            XMLSlideShow xmlSlideShow = new XMLSlideShow(is);
            is.close();
            // 获取幻灯片
            List<XSLFSlide> slides = xmlSlideShow.getSlides();
            for (int i = 0; i < slides.size(); i++) {
                Dimension pgsize = xmlSlideShow.getPageSize();
                // 解决乱码问题
                List<XSLFShape> shapes = slides.get(i).getShapes();
                for (XSLFShape shape : shapes) {
                    if (shape instanceof XSLFTextShape) {
                        XSLFTextShape sh = (XSLFTextShape) shape;
                        List<XSLFTextParagraph> textParagraphs = sh.getTextParagraphs();
                        for (XSLFTextParagraph xslfTextParagraph : textParagraphs) {
                            List<XSLFTextRun> textRuns = xslfTextParagraph.getTextRuns();
                            for (XSLFTextRun xslfTextRun : textRuns) {
                                xslfTextRun.setFontFamily("宋体");
                            }
                        }
                    }
                }
                // 根据幻灯片大小生成图片
                BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
                // 最核心的代码
                slides.get(i).draw(createGraphics(pgsize, img));
                // 图片将要存放的路径
                String nameStr = (filePrefix == null ? "" : (filePrefix + "_")) + UUID.randomUUID().toString();
                String absolutePath = imgFilePath.getAbsolutePath() + "/" + nameStr + "." + PNG;
                File jpegFile = new File(absolutePath);
                // 如果图片存在，则不再生成
                if (jpegFile.exists()) {
                    continue;
                }
                log.info("==>" + jpegFile.getPath());
                list.add(StringUtils.isBlank(returnPath) ? absolutePath : (returnPath + nameStr + "." + PNG));
                // 这里设置图片的存放路径和图片的格式(jpeg,png,bmp等等),注意生成文件路径
                FileOutputStream out = new FileOutputStream(jpegFile);
                // 缩放图片比例
                reSize(pgsize);
                // 写入到图片中去
                ImageIO.write(scale(pgsize, img), PNG, out);
                out.close();
                xmlSlideShow.close();
            }
            log.info("PPT转换成图片 成功！");
        } catch (Exception e) {
            log.error("PPT转换成图片 发生异常！", e);
            throw new Exception("PPT转换成图片 发生异常！");
        }
        return list;

    }

    /**
     * 创建图画
     * 
     * @Title: createGraphics
     * @param pgsize
     * @param img
     * @return
     *
     */
    public static Graphics2D createGraphics(Dimension pgsize, BufferedImage img) {
        Graphics2D graphics = img.createGraphics();
        graphics.setPaint(Color.white);
        graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
        return graphics;
    }

    /**
     * 图片缩放处理
     * 
     * @Title: scale
     * @param pgsize
     * @param img
     * @return
     *
     */
    public static BufferedImage scale(Dimension pgsize, BufferedImage img) {
        BufferedImage newImg = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = newImg.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(img, 0, 0, pgsize.width, pgsize.height, null);
        g.dispose();
        return newImg;
    }

    /**
     * 缩放图片尺寸
     * 
     * @Title: reSize
     * @param pgsize
     *
     */
    public static void reSize(Dimension pgsize) {
        // 16:9
        Double scale169 = new Double(9d / 16d);
        // 4:3
        Double scale43 = new Double(3d / 4d);
        // 用户自定义的宽度
        Double userScale = new Double(new Double(pgsize.height) / new Double(pgsize.width));
        if (scale169.equals(userScale)) {
            pgsize.height = 1080;
            pgsize.width = 1920;
        } else if (scale43.equals(userScale)) {
            pgsize.height = 1440;
            pgsize.width = 1920;
        }
    }
}
