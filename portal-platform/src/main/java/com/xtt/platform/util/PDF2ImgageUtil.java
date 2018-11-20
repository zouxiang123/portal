package com.xtt.platform.util;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.icepdf.core.pobjects.Document;
import org.icepdf.core.pobjects.Page;
import org.icepdf.core.util.GraphicsRenderingHints;

/**
 * Pdf转成成图片
 * 
 * @author dzw
 *
 */
public class PDF2ImgageUtil {

    private static Logger log = Logger.getLogger(PDF2ImgageUtil.class);
    private static final String PNG = "png";

    public static void main(String[] args) {
        try {
            File file = new File("d:\\doc\\2.pdf");
            File savePath = new File("d:\\doc\\");
            // doPDFtoImage(file, savePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * pdf 转换成图片
     * 
     * @param filePath
     *            文件目录
     * @param savePath
     *            存储目录
     * @return 图片存储路径地址
     */
    public static List<String> doPDFtoImage(File filePath, File savePath, String returnPath, String filePrefix) throws Exception {
        List<String> list = new ArrayList<String>();
        if (!savePath.exists()) {
            savePath.mkdir();
        }
        Document document = new Document();
        try {
            document.setFile(filePath.getPath());
        } catch (Exception ex) {
        }
        float scale = 1.3f;
        float rotation = 0f;
        log.debug("当前pdf总页数：" + document.getNumberOfPages());
        for (int i = 0; i < document.getNumberOfPages(); i++) {
            log.debug("第" + i + "页。");
            BufferedImage image = null;
            try {
                image = (BufferedImage) document.getPageImage(i, GraphicsRenderingHints.SCREEN, Page.BOUNDARY_CROPBOX, rotation, scale);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            RenderedImage rendImage = image;
            try {
                String nameStr = (filePrefix == null ? "" : (filePrefix + "_")) + UUID.randomUUID().toString();
                String saveFilePath = savePath + "/" + nameStr + "." + PNG;
                File file = new File(saveFilePath);
                ImageIO.write(rendImage, PNG, file);
                list.add(StringUtils.isBlank(returnPath) ? saveFilePath : (returnPath + nameStr + "." + PNG));
                log.debug("PDF转换成图片 成功！");
            } catch (Exception e) {
                log.error("PDF转换成图片 发生异常！", e);
                throw new Exception();
            }
            image.flush();
        }

        document.dispose();
        return list;
    }
}
