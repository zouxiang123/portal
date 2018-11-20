package com.xtt.platform.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Function : 文件压缩成zip
 * 
 * @author : lqf
 * @Date : 2015-12-15
 */
public class ZipUtil {
    private static final Logger logger = Logger.getLogger(ZipUtil.class);

    static int k = 1; // 定义递归次数变量

    public ZipUtil() {
    }

    /**
     * 压缩指定的单个或多个文件，如果是目录，则遍历目录下所有文件进行压缩
     * 
     * @param zipFilePath
     *            ZIP文件名包含全路径
     * @param files
     *            文件列表
     */
    @SuppressWarnings("unused")
    public static boolean zip(String zipFilePath, File... files) {
        logger.info("压缩: " + zipFilePath);
        ZipOutputStream out = null;
        BufferedOutputStream bo = null;
        try {
            createDir(zipFilePath);
            out = new ZipOutputStream(new FileOutputStream(zipFilePath));
            for (int i = 0; i < files.length; i++) {
                if (null != files[i]) {
                    zip(out, files[i], files[i].getName());
                }
            }
            out.close(); // 输出流关闭
            logger.info("压缩完成");
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    /**
     * 压缩指定的单个或多个文件，如果是目录，则遍历目录下所有文件进行压缩
     * 
     * @param zipFilePath
     *            ZIP文件名包含全路径
     * @param zipDirectory
     *            压缩文件外层的文件夹
     * @param files
     *            文件列表
     */
    public static boolean zip(String zipFilePath, String zipDirectory, List<String> files) {
        logger.info("压缩: " + zipFilePath);
        ZipOutputStream out = null;
        try {
            createDir(zipFilePath);
            out = new ZipOutputStream(new FileOutputStream(zipFilePath));
            for (int i = 0; i < files.size(); i++) {
                if (null != files.get(i)) {
                    zipFile(out, new File(files.get(i)), files.get(i), zipDirectory);
                }
            }
            out.close(); // 输出流关闭
            logger.info("压缩完成");
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    /**
     * 执行压缩
     * 
     * @param out
     *            ZIP输入流
     * @param f
     *            被压缩的文件
     * @param base
     *            被压缩的文件名
     */
    private static void zip(ZipOutputStream out, File f, String base) { // 方法重载
        try {
            if (f.isDirectory()) {// 压缩目录
                try {
                    File[] fl = f.listFiles();
                    if (fl.length == 0) {
                        out.putNextEntry(new ZipEntry(base + "/")); // 创建zip实体
                        logger.info(base + "/");
                    }
                    for (int i = 0; i < fl.length; i++) {
                        zip(out, fl[i], base + "/" + fl[i].getName()); // 递归遍历子文件夹
                    }
                    // System.out.println("第" + k + "次递归");
                    k++;
                } catch (IOException e) {
                    logger.error(e.getMessage());
                    e.printStackTrace();
                }
            } else { // 压缩单个文件
                logger.info(base);
                out.putNextEntry(new ZipEntry(base)); // 创建zip实体
                FileInputStream in = new FileInputStream(f);
                BufferedInputStream bi = new BufferedInputStream(in);
                int b;
                while ((b = bi.read()) != -1) {
                    out.write(b); // 将字节流写入当前zip目录
                }
                out.closeEntry(); // 关闭zip实体
                in.close(); // 输入流关闭
            }

        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 压缩
     * 
     * @Title: zipFile
     * @param out
     * @param f
     * @param base
     *
     */
    private static void zipFile(ZipOutputStream out, File f, String base, String zipDirectory) { // 方法重载
        try {
            if (!f.isDirectory()) {// 不压缩压缩目录
                logger.info(base);
                // 去掉多余的压缩目录
                base = base.substring(base.lastIndexOf("/") + 1, base.length());
                out.putNextEntry(new ZipEntry(zipDirectory + "/" + base)); // 创建zip实体
                FileInputStream in = new FileInputStream(f);
                BufferedInputStream bi = new BufferedInputStream(in);
                int b;
                while ((b = bi.read()) != -1) {
                    out.write(b); // 将字节流写入当前zip目录
                }
                out.closeEntry(); // 关闭zip实体
                in.close(); // 输入流关闭
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 目录不存在时，先创建目录
     * 
     * @param zipFileName
     */
    public static void createDir(String zipFileName) {
        String filePath = null;
        if (zipFileName.indexOf("/") > -1) {
            filePath = StringUtils.substringBeforeLast(zipFileName, "/");
        } else {
            filePath = StringUtils.substringBeforeLast(zipFileName, "\\");
        }
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {// 目录不存在时，先创建目录
            targetFile.mkdirs();
        }
    }

    /**
     * 
     * zip解压
     * 
     * @param srcFile
     *            zip源文件
     * 
     * @param destDirPath
     *            解压后的目标文件夹
     * 
     * @throws RuntimeException
     *             解压失败会抛出运行时异常
     * 
     */

    public static void unZip(File srcFile, String destDirPath) throws RuntimeException {
        long start = System.currentTimeMillis();
        // 判断源文件是否存在
        if (!srcFile.exists()) {
            throw new RuntimeException(srcFile.getPath() + "所指文件不存在");
        }
        File destDir = new File(destDirPath);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        // 开始解压
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(srcFile);
            Enumeration<?> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                logger.info("解压" + entry.getName());
                // 如果是文件夹，就创建个文件夹
                if (entry.isDirectory()) {
                    String dirPath = destDirPath + "/" + entry.getName();
                    File dir = new File(dirPath);
                    dir.mkdirs();
                } else {
                    // 如果是文件，就先创建一个文件，然后用io流把内容copy过去
                    File targetFile = new File(destDirPath + "/" + entry.getName());
                    // 保证这个文件的父文件夹必须要存在
                    if (!targetFile.getParentFile().exists()) {
                        targetFile.getParentFile().mkdirs();
                    }
                    targetFile.createNewFile();
                    // 将压缩文件内容写入到这个文件中
                    InputStream is = zipFile.getInputStream(entry);
                    FileOutputStream fos = new FileOutputStream(targetFile);
                    int len;
                    byte[] buf = new byte[2048];
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    // 关流顺序，先打开的后关闭
                    fos.close();
                    is.close();
                }
            }
            long end = System.currentTimeMillis();
            logger.info("解压完成，耗时：" + (end - start) + " ms");
        } catch (Exception e) {
            throw new RuntimeException("unzip error from ZipUtils", e);
        } finally {
            if (zipFile != null) {
                try {
                    zipFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {

            // ZipUtil.zip("D:\\xtt\\10101\\tempZip\\tempZip.zip", new File("D:\\xtt\\10101\\tempZip\\0a2647a9-f038-4621-ada8-3d40f2a73c2b.jpg")); //
            // 测试单个文件
            // ZipUtil.unZip(new File("D:/xtt/10101/temp/f82b868e-e239-4332-8d18-33211679f83e.zip"), "D:/xtt/10101/temp/");
            File file = new File("D:/xtt/10101/temp/f82b868e-e239-4332-8d18-33211679f83e");
            String[] listFile = file.list();
            for (String temp : listFile) {
                System.out.println(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}