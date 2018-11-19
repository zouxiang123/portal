/**   
 * @Title: ZipEncryptUtil.java 
 * @Package com.xtt.platform.util
 * Copyright: Copyright (c) 2015
 * @author: xyw   
 * @date: 2018年10月16日 上午11:55:06 
 *
 */
package com.xtt.platform.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.common.io.Files;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

/**
 * 加解密zip util
 * 
 * @ClassName: ZipEncryptUtil
 * @date: 2018年10月16日 上午11:55:25
 * @version: V1.0
 *
 */
public class ZipEncryptUtil {
    /**
     * 压缩
     * 
     * @Title: zip
     * @param origFile
     *            源目录 文件或文件夹
     * @param destFile
     *            要压缩到的文件
     * @param passwd
     *            密码 不是必填
     * @throws ZipException
     *
     */
    public static void zip(String origFile, String destFile, String passwd) throws ZipException {
        File srcfile = new File(origFile);

        // 创建目标文件
        String destname = buildDestFileName(srcfile, destFile);
        ZipParameters par = new ZipParameters();
        par.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        par.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

        if (passwd != null && !"".equals(passwd)) {
            par.setEncryptFiles(true);
            par.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
            par.setPassword(passwd.toCharArray());
        }

        ZipFile zipfile = new ZipFile(destname);
        if (srcfile.isDirectory()) {
            zipfile.addFolder(srcfile, par);
        } else {
            zipfile.addFile(srcfile, par);
        }
    }

    /**
     * 压缩
     * 
     * @Title: zip
     * @param origFiles
     *            文件
     * @param destFile
     *            目标路径
     * @param passwd
     *            密码
     * @throws ZipException
     *
     */
    public static void zip(List<String> origFiles, String destFile, String passwd) throws ZipException {
        ZipParameters par = new ZipParameters();
        par.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        par.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

        if (passwd != null && !"".equals(passwd)) {
            par.setEncryptFiles(true);
            par.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
            par.setPassword(passwd.toCharArray());
        }
        ArrayList<File> srcFiles = new ArrayList<File>();
        for (String srcFileStr : origFiles) {
            File srcfile = new File(srcFileStr);
            if (!srcfile.isDirectory()) {
                srcFiles.add(srcfile);
            }
        }
        createPath(destFile);// 路径的创建
        if (destFile.endsWith("/")) {
            destFile += UUID.randomUUID().toString() + ".zip";
        }
        // 创建目标文件
        ZipFile zipfile = new ZipFile(destFile);
        zipfile.addFiles(srcFiles, par);

    }

    /**
     * 解压
     * 
     * @param zipfile
     *            压缩包文件
     * @param dest
     *            目标文件
     * @param passwd
     *            密码
     * @throws ZipException
     *             抛出异常
     */
    public static void unZip(String zipfile, String dest, String passwd) throws ZipException {
        ZipFile zfile = new ZipFile(zipfile);
        zfile.setFileNameCharset("UTF-8");// 在GBK系统中需要设置
        if (!zfile.isValidZipFile()) {
            throw new ZipException("压缩文件不合法，可能已经损坏！");
        }

        File file = new File(dest);
        if (file.isDirectory() && !file.exists()) {
            file.mkdirs();
        }

        if (zfile.isEncrypted()) {
            zfile.setPassword(passwd.toCharArray());
        }
        zfile.extractAll(dest);
    }

    public static String buildDestFileName(File srcfile, String dest) {
        if (dest == null) {
            if (srcfile.isDirectory()) {
                dest = srcfile.getParent() + "/" + srcfile.getName() + ".zip";
            } else {
                String filename = srcfile.getName().substring(0, srcfile.getName().lastIndexOf("."));
                dest = srcfile.getParent() + "/" + filename + ".zip";
            }
        } else {
            createPath(dest);// 路径的创建
            if (dest.endsWith("/")) {
                String filename = "";
                if (srcfile.isDirectory()) {
                    filename = srcfile.getName();
                } else {
                    filename = srcfile.getName().substring(0, srcfile.getName().lastIndexOf("."));
                }
                dest += filename + ".zip";
            }
        }
        return dest;
    }

    private static void createPath(String dest) {
        File destDir = null;
        if (dest.endsWith("/")) {
            destDir = new File(dest);// 给出的是路径时
        } else {
            destDir = new File(dest.substring(0, dest.lastIndexOf("/")));
        }
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
    }

    /**
     * 文件移动
     * 
     * @Title: copyTo
     * @param fromList
     *            文件
     * @param to
     *            文件夹
     * @throws Exception
     *
     */
    public static void copyToDir(List<String> fromList, String to) throws Exception {
        if (fromList != null && fromList.size() > 0) {
            File toDir = new File(to);
            if (!toDir.exists()) {
                toDir.mkdirs();
            }
            for (String from : fromList) {
                File fromFile = new File(from);
                File toFile = new File(to + "/" + fromFile.getName());
                if (!toFile.exists() && fromFile.exists()) {
                    toFile.createNewFile();
                    Files.copy(fromFile, toFile);
                }
            }
        }
    }
}
