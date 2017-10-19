package com.xtt.platform.util;

/*package com.xtt.platform.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import org.apache.log4j.Logger;

import com.xtt.platform.util.config.CusotmizedPropertyUtil;
import com.xtt.platform.util.model.MultiMediaConfigModel;

*//**
   * 
   * @ClassName: FtpUtil
   * @description: FTP上传下载帮助类
   * @author: Tik
   * @date: 2014年6月12日 下午3:23:28
   * @version: V1.0
   * 
   */
/*
public class FtpUtil {

private static Logger logger = Logger.getLogger(FtpUtil.class);

private static MultiMediaConfigModel init(MultiMediaConfigModel model) {

    if (null == model) {
        model = new MultiMediaConfigModel();
    }

    String ip = CusotmizedPropertyUtil.getContextProperty("FTP.IP")
            .toString();
    String name = CusotmizedPropertyUtil.getContextProperty("FTP.NAME")
            .toString();
    String pwd = CusotmizedPropertyUtil.getContextProperty("FTP.PWD")
            .toString();
    int port = new Integer(CusotmizedPropertyUtil.getContextProperty(
            "FTP.PORT").toString()).intValue();
    model.setAccWay("1");
    model.setId("1");
    model.setFtpAdd(ip);
    model.setFtpPassword(pwd);
    model.setFtpUserName(name);
    model.setFtpPort(port);
    return model;
}

*//**
   * 
   * 
   * @Title: downloadFromFTP @Description:
   *         <p>
   *         FTp下载,下载整个目录
   *         <p>
   * 
   *         <pre>
   *  这里描述这个方法的使用方法 – 可选
   *         </pre>
   * 
   * @param: <p>
   * @param model
   * @param: <p>
   * @param tenantId
   *            <p>
   * @date: 2014年6月12日 @return: void @throws
   * 
   */
/*
public static void downloadFromFTP(MultiMediaConfigModel model,
    String tenantId) {
if (null != model) {
    *//**
       * --------------------- 如果本地目录不存在，创建。 如果存在，删除本地文件 --------------------
       */
/*
String localPath = model.getDownloadPath();
String serverPath = model.getServerPath();
localPath = serverPath + localPath;
if (!localPath.endsWith("/")) {
localPath = localPath + "/";
}
localPath = localPath + tenantId + "/";
File localDir = new File(localPath);
if (!localDir.exists()) {
localDir.mkdirs();
} else {
File[] files = localDir.listFiles();
if (files != null) {
    for (int j = 0; j < files.length; j++) {
        File localFile = files[j];
        localFile.delete();
    }
}
}

*//**
   * --------------------- 获取远程路径及FTP连接参数 将FTP上的文件下载到本地 --------------------
   */
/*
String remotePath = model.getFtpFilePath();
if (!remotePath.endsWith("/")) {
remotePath = remotePath + "/";
}
String ftpIp = model.getFtpAdd();
String userName = model.getFtpUserName();
String password = model.getFtpPassword();
FTPClient fc = new FTPClient();

try {
fc.connect(ftpIp, model.getFtpPort());
boolean login = fc.login(userName, password);
if (login) {
    String realPath = "";
    if(null!=tenantId & !"".equals(tenantId)){
        if ("/".equals(remotePath)) {
            realPath = tenantId + "/";
        } else {
            realPath = remotePath + tenantId + "/";
        }
    }else{
        if ("/".equals(remotePath)) {
            realPath ="/";
        } else {
            realPath = remotePath +"/";
        }
    }
    FTPFile[] ftpFiles = fc.listFiles(realPath);
    if (ftpFiles != null) {
        FileOutputStream fos = null;
        for (int i = 0; i < ftpFiles.length; i++) {
            FTPFile ftpFile = ftpFiles[i];
            String ftpFileName = ftpFile.getName();
            if (ftpFile.isFile()) {
                String localFilePath = localPath + ftpFileName;
                fos = new FileOutputStream(localFilePath);
                fc.setFileType(FTPClient.BINARY_FILE_TYPE);
                fc.retrieveFile(realPath + ftpFileName, fos);
                fos.flush();
            }

            if (fos != null) {
                fos.close();
            }
        }
    }
}
fc.disconnect();
} catch (Exception e) {
logger.error(e.toString());
}
}
}

*//**
   * 
   * 
   * @Title: downloadFromFTP @Description:
   *         <p>
   *         下载指定的文件
   *         <p>
   * 
   *         <pre>
   *  这里描述这个方法的使用方法 – 可选
   *         </pre>
   * 
   * @param: <p>
   * @param model
   * @param: <p>
   * @param tenantId
   * @param: <p>
   * @param fileName
   * @param: <p>
   * @return
   *         <p>
   * @date: 2014年6月12日 @return: String @throws
   * 
   */
/*
public static String downloadFromFTP(MultiMediaConfigModel model,
    String tenantId, String fileName) {
String localFilePath = "";
model=FtpUtil.init(model);
if (model != null) {
    *//**
       * ------------------------ 如果本地目录不存在，创建目录 如果存在，删除本地同名文件 -----------------------
       */
/*
String localPath = model.getDownloadPath();
String serverPath = model.getServerPath();
if(null!=serverPath && !"".equals(serverPath)){
localPath = serverPath + localPath;
}
if (!localPath.endsWith("/")) {
localPath = localPath + "/";
}

localPath = localPath + tenantId + "/";
File localDir = new File(localPath);
if (!localDir.exists()) {
localDir.mkdirs();
} else {
File[] files = localDir.listFiles();
if (files != null) {
    for (int j = 0; j < files.length; j++) {
        File localFile = files[j];
        if (localFile.getName().equals(fileName)) {
            localFile.delete();
        }
    }
}
}

*//**
   * --------------------- 获取远程路径及FTP连接参数 将FTP上的文件下载到本地 --------------------
   */
/*
String remotePath = model.getFtpFilePath();
if (!remotePath.endsWith("/")) {
remotePath = remotePath + "/";
}
String ftpIp = model.getFtpAdd();
String userName = model.getFtpUserName();
String password = model.getFtpPassword();

FTPClient fc = new FTPClient();
try {
fc.connect(ftpIp, model.getFtpPort());
boolean login = fc.login(userName, password);
if (login) {
    String realPath = "";
    if(null!=tenantId & !"".equals(tenantId)){
        if ("/".equals(remotePath)) {
            realPath = tenantId + "/";
        } else {
            realPath = remotePath + tenantId + "/";
        }
    }else{
        if ("/".equals(remotePath)) {
            realPath ="/";
        } else {
            realPath = remotePath +"/";
        }
    }
    
    FTPFile[] ftpFiles = fc.listFiles(realPath);
    if (ftpFiles != null) {
        FileOutputStream fos = null;
        for (int i = 0; i < ftpFiles.length; i++) {
            FTPFile ftpFile = ftpFiles[i];
            String ftpFileName = ftpFile.getName();
            if (ftpFile.isFile()
                    && ftpFile.getName().equals(fileName)) {
                localFilePath = localPath + ftpFileName;
                fos = new FileOutputStream(localFilePath);
                fc.setFileType(FTPClient.BINARY_FILE_TYPE);
                fc.retrieveFile(realPath + ftpFileName, fos);
                fos.flush();
            }

            if (fos != null) {
                fos.close();
            }
        }
    }
}

fc.disconnect();
} catch (Exception e) {
logger.error(e.toString());
}
}

return localFilePath;
}

*//**
   * 
   * 
   * @Title: uploadToFTP @Description:
   *         <p>
   *         上传文件到FTP
   *         <p>
   * 
   *         <pre>
   *  这里描述这个方法的使用方法 – 可选
   *         </pre>
   * 
   * @param: <p>
   * @param model
   * @param: <p>
   * @param tenantId
   *            <p>
   * @date: 2014年6月12日 @return: void @throws
   * 
   */
/*
public static void uploadToFTP(MultiMediaConfigModel model, String tenantId) {
if (model != null) {
    String localPath = model.getDownloadPath();
    String serverPath = model.getServerPath();
    localPath = serverPath + localPath;
    if (!localPath.endsWith("/")) {
        localPath = localPath + "/";
    }

    localPath = localPath + tenantId;
    String remotePath = model.getFtpFilePath();
    if (!remotePath.endsWith("/")) {
        remotePath = remotePath + "/";
    }
    FTPClient fc = new FTPClient();

    String[] ftpInfo = model.getFtpAdd().split(":");
    try {
        if (ftpInfo.length > 1) {
            fc.connect(ftpInfo[0], Integer.parseInt(ftpInfo[1]));
        } else {
            fc.connect(ftpInfo[0]);
        }

        boolean login = fc.login(model.getFtpUserName(),
                model.getFtpPassword());

        if (login) {
            *//** 上传前,先删除FTP上原有文件,以保持更新 */
/*
String ftpPath = "";
if ("/".equals(remotePath)) {
ftpPath = tenantId + "/";
} else {
ftpPath = remotePath + tenantId + "/";
}
FTPFile[] ftpFiles = fc.listFiles(ftpPath);
if (ftpFiles != null) {
for (int i = 0; i < ftpFiles.length; i++) {
fc.deleteFile(ftpPath + ftpFiles[i].getName());
}
}

*//** 上传本地文件到FTP */
/*
File localDir = new File(localPath);
File[] localFiles = localDir.listFiles();

if (localFiles != null) {
FileInputStream fis = null;
for (int j = 0; j < localFiles.length; j++) {
fis = new FileInputStream(localDir + "/"
+ localFiles[j].getName());
boolean change = fc.changeWorkingDirectory(ftpPath);
if (!change) {
fc.makeDirectory(ftpPath);
fc.changeWorkingDirectory(ftpPath);
}

fc.setFileType(FTPClient.BINARY_FILE_TYPE);
fc.setBufferSize(1024);
fc.setControlEncoding("utf-8");
fc.storeFile(localFiles[j].getName(), fis);

fis.close();
}
}
}
} catch (Exception e) {
logger.error(e.toString());
}
}
}

*//**
   * --------------------------------------------------------------------- 向FTP上传指定文件到配置好的目录中
   * 
   * @param mmc
   *            配置对象<该对象可通过getMultiMediaConfig(String configId)获得>
   * @param tenantId
   *            租户ID,系统资源按租户存放
   * @param fileName
   *            本地文件名
   * @return void 将指定文件上传到FTP指定目录下,无返回值 ---------------------------------------- -----------------------------
   */
/*
public static void uploadToFTP(MultiMediaConfigModel model,
    String tenantId, String fileName) {
if (model != null) {
    String localPath = model.getDownloadPath();
    String serverPath = model.getServerPath();
    localPath = serverPath + localPath;
    if (!localPath.endsWith("/")) {
        localPath = localPath + "/";
    }

    localPath = localPath + tenantId;
    String remotePath = model.getFtpFilePath();
    if (!remotePath.endsWith("/")) {
        remotePath = remotePath + "/";
    }
    FTPClient fc = new FTPClient();

    String[] ftpInfo = model.getFtpAdd().split(":");
    try {
        if (ftpInfo.length > 1) {
            fc.connect(ftpInfo[0], Integer.parseInt(ftpInfo[1]));
        } else {
            fc.connect(ftpInfo[0]);
        }

        boolean login = fc.login(model.getFtpUserName(),
                model.getFtpPassword());

        if (login) {
            *//** 上传前,先删除FTP上原有同名文件,以保持更新 */
/*
String ftpPath = "";
if ("/".equals(remotePath)) {
ftpPath = tenantId + "/";
} else {
ftpPath = remotePath + tenantId + "/";
}
FTPFile[] ftpFiles = fc.listFiles(ftpPath);
if (ftpFiles != null) {
for (int i = 0; i < ftpFiles.length; i++) {
if (ftpFiles[i].getName().equals(fileName)) {
fc.deleteFile(ftpPath + ftpFiles[i].getName());
}
}
}

*//** 上传本地文件到FTP */
/*
File localDir = new File(localPath);
File[] localFiles = localDir.listFiles();

if (localFiles != null) {
FileInputStream fis = null;
for (int j = 0; j < localFiles.length; j++) {
if (localFiles[j].getName().equals(fileName)) {
fis = new FileInputStream(localDir + "/"
+ localFiles[j].getName());
boolean change = fc
.changeWorkingDirectory(ftpPath);
if (!change) {
fc.makeDirectory(ftpPath);
fc.changeWorkingDirectory(ftpPath);
}

fc.setFileType(FTPClient.BINARY_FILE_TYPE);
fc.setBufferSize(1024);
fc.setControlEncoding("utf-8");
fc.storeFile(localFiles[j].getName(), fis);

fis.close();
}
}
}
}
} catch (Exception e) {
logger.error(e.toString());
}
}
}

*//**
   * --------------------------------------------------------------------- 向FTP上传指定文件到配置好的目录中
   * 
   * @param mmc
   *            配置对象<该对象可通过getMultiMediaConfig(String configId)获得>
   * @param tenantId
   *            租户ID,系统资源按租户存放
   * @param localFile
   *            本地文件
   * @param remoteFileName
   *            远程文件名
   * @return void 将指定文件上传到FTP指定目录下,无返回值 ---------------------------------------- -----------------------------
   */
/*
public static void uploadToFTP(MultiMediaConfigModel model,
    String tenantId, File localFile, String remoteFileName) {
if (model != null) {
    String remotePath = model.getFtpFilePath();
    if (!remotePath.endsWith("/")) {
        remotePath = remotePath + "/";
    }
    FTPClient fc = new FTPClient();

    String[] ftpInfo = model.getFtpAdd().split(":");
    try {
        if (ftpInfo.length > 1) {
            fc.connect(ftpInfo[0], Integer.parseInt(ftpInfo[1]));
        } else {
            fc.connect(ftpInfo[0]);
        }

        boolean login = fc.login(model.getFtpUserName(),
                model.getFtpPassword());

        if (login) {
            *//** 上传前,先删除FTP上同名文件,以保持更新 */
/*
String ftpPath = "";
if ("/".equals(remotePath)) {
ftpPath = tenantId + "/";
} else {
ftpPath = remotePath + tenantId + "/";
}
FTPFile[] ftpFiles = fc.listFiles(ftpPath);
if (ftpFiles != null) {
for (int i = 0; i < ftpFiles.length; i++) {
if (ftpFiles[i].getName().equals(remoteFileName)) {
fc.deleteFile(ftpPath + ftpFiles[i].getName());
}
}
}

*//** 上传本地文件到FTP */
/*
if (localFile != null) {
FileInputStream fis = new FileInputStream(
localFile.toString());
boolean change = fc.changeWorkingDirectory(ftpPath);
if (!change) {
fc.makeDirectory(ftpPath);
fc.changeWorkingDirectory(ftpPath);
}

fc.setFileType(FTPClient.BINARY_FILE_TYPE);
fc.setBufferSize(1024);
fc.setControlEncoding("utf-8");
fc.storeFile(remoteFileName, fis);

fis.close();
}
}
} catch (Exception e) {
logger.error("向FTP上传指定文件到配置好的目录中出错" + e.getMessage(), e);
} finally {
try {
fc.disconnect();
} catch (IOException e) {
logger.error("关闭FTP连接出错" + e.getMessage(), e);
}
}
}
}

*//**
   * --------------------------------------------------------------------- 〈验证文件是否存在，并且从FTP上把文件写入到临时文件夹〉
   * 
   * @param mmc
   *            配置对象<该对象可通过getMultiMediaConfig(String configId)获得>
   * @param tenantId
   *            租户ID,系统资源按租户存放
   * @param fileName
   *            需要下载的文件
   * @return String 全路径 -------------------------------------------------------- -------------
   */
/*
public static String validateFtpFileIsExist(MultiMediaConfigModel model,
    String tenantId, String fileName) {
String localFilePath = "";
try {
    *//**
       * ------------------------ 如果本地目录不存在，创建目录 如果存在，删除本地同名文件 -----------------------
       */
/*
String localPath = model.getDownloadPath();
String serverPath = model.getServerPath();
localPath = serverPath + localPath;
if (!localPath.endsWith("/")) {
localPath = localPath + "/";
}
localPath = localPath + tenantId + "/";
File localDir = new File(localPath);
if (!localDir.exists()) {
localDir.mkdirs();
}

*//**
   * --------------------- 获取远程路径及FTP连接参数 将FTP上的文件下载到本地 --------------------
   */
/*
String remotePath = model.getFtpFilePath();
if (!remotePath.endsWith("/")) {
remotePath = remotePath + "/";
}
String ftpIp = model.getFtpAdd();
String userName = model.getFtpUserName();
String password = model.getFtpPassword();

FTPClient fc = new FTPClient();
String[] ftpInfo = ftpIp.split(":");

if (ftpInfo.length > 1) {
fc.connect(ftpInfo[0], Integer.parseInt(ftpInfo[1]));
} else {
fc.connect(ftpInfo[0]);
}

boolean login = fc.login(userName, password);
if (login) {
String realPath = "";
if ("/".equals(remotePath)) {
    realPath = tenantId + "/";
} else {
    realPath = remotePath + tenantId + "/";
}
FTPFile[] ftpFiles = fc.listFiles(realPath);
if (ftpFiles != null) {
    FileOutputStream fos = null;
    for (int i = 0; i < ftpFiles.length; i++) {
        FTPFile ftpFile = ftpFiles[i];
        String ftpFileName = ftpFile.getName();
        if (ftpFile.isFile() && ftpFileName.equals(fileName)) {
            localFilePath = localPath + ftpFileName;
            fos = new FileOutputStream(localFilePath);
            fc.setFileType(FTPClient.BINARY_FILE_TYPE);
            fc.retrieveFile(realPath + ftpFileName, fos);
            fos.flush();
        }

        if (fos != null) {
            fos.close();
        }
    }
}
fc.disconnect();
}
} catch (Exception e) {
logger.error("验证FTP文件是否存在出错." + e.toString(), e);// 连接或读文件出错
} finally {
return localFilePath;
}

}

*//**
   * --------------------------------------------------------------------- 〈从FTP下载指定文件到配置好的目录中,并把文件内容读到输出流中〉
   * 
   * @param mmc
   *            配置对象<该对象可通过getMultiMediaConfig(String configId)获得>
   * @param tenantId
   *            租户ID,系统资源按租户存放
   * @param fileName
   *            需要下载的文件
   * @param response
   *            response
   * @return void -------------------------------------------------------------- -------
   */
/*
public static void downloadToOutputStream(MultiMediaConfigModel mmc,
    String tenantId, String fileName, HttpServletResponse response) {
String localFilePath = "";
if (mmc != null) {
    *//**
       * ------------------------ 如果本地目录不存在，创建目录 如果存在，删除本地同名文件 -----------------------
       */
/*
String localPath = mmc.getDownloadPath();
String serverPath = mmc.getServerPath();
localPath = serverPath + localPath;
if (!localPath.endsWith("/")) {
localPath = localPath + "/";
}
localPath = localPath + tenantId + "/";
File localDir = new File(localPath);
if (!localDir.exists()) {
localDir.mkdirs();
}

*//**
   * --------------------- 获取远程路径及FTP连接参数 将FTP上的文件下载到本地 --------------------
   */
/*
String remotePath = mmc.getFtpFilePath();
if (!remotePath.endsWith("/")) {
remotePath = remotePath + "/";
}
String ftpIp = mmc.getFtpAdd();
String userName = mmc.getFtpUserName();
String password = mmc.getFtpPassword();

FTPClient fc = new FTPClient();
String[] ftpInfo = ftpIp.split(":");
try {
if (ftpInfo.length > 1) {
    fc.connect(ftpInfo[0], Integer.parseInt(ftpInfo[1]));
} else {
    fc.connect(ftpInfo[0]);
}

boolean login = fc.login(userName, password);
if (login) {
    String realPath = "";
    if ("/".equals(remotePath)) {
        realPath = tenantId + "/";
    } else {
        realPath = remotePath + tenantId + "/";
    }
    FTPFile[] ftpFiles = fc.listFiles(realPath);
    if (ftpFiles != null) {
        FileOutputStream fos = null;
        for (int i = 0; i < ftpFiles.length; i++) {
            FTPFile ftpFile = ftpFiles[i];
            String ftpFileName = ftpFile.getName();
            if (ftpFile.isFile()
                    && ftpFileName.equals(fileName)) {
                localFilePath = localPath + ftpFileName;
                fos = new FileOutputStream(localFilePath);
                fc.setFileType(FTPClient.BINARY_FILE_TYPE);
                fc.retrieveFile(realPath + ftpFileName, fos);
                fos.flush();
            }

            if (fos != null) {
                fos.close();
            }
        }

        File local = new File(localFilePath);
        {
            if (local.exists()) {
                FileInputStream fis = null;
                OutputStream out = null;
                try {
                    response.reset();
                    response.setContentType("application/x-download");
                    response.setHeader("content-disposition",
                            "attachment; filename=" + fileName);
                    fis = new FileInputStream(local);
                    out = response.getOutputStream();
                    byte[] bs = new byte[1024];
                    int len = 0;
                    while ((len = fis.read(bs)) > 0) {
                        out.write(bs, 0, len);
                    }

                    out.flush();
                } catch (Exception e) {
                    logger.error("FTP下载文件出错，" + e.toString(), e);
                }

                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            } else {
                FtpUtil.returnMsg(response, "error");// 文件不存在
            }
        }
    }

    fc.disconnect();
}

} catch (Exception e) {
logger.error(e.toString());// 连接或读文件出错
}

} else {
FtpUtil.returnMsg(response, "error");// 查询配置出错(可能数据库问题)
}

}

*//**
   * --------------------------------------------------------------------- 〈从FTP下载指定文件到配置好的目录中,并把文件内容读到输出流中,并默认保存为指定的文件名〉
   * 
   * @param mmc
   *            配置对象<该对象可通过getMultiMediaConfig(String configId)获得>
   * @param tenantId
   *            租户ID,系统资源按租户存放
   * @param fileName
   *            需要下载的文件
   * @param downloadName
   *            下载保存的文件名
   * @param response
   *            response
   * @return void -------------------------------------------------------------- -------
   */
/*
public static void downloadToOutputStream(MultiMediaConfigModel mmc,
    String tenantId, String fileName, String downloadName,
    HttpServletResponse response) {
String localFilePath = "";
if (mmc != null) {
    *//**
       * ------------------------ 如果本地目录不存在，创建目录 如果存在，删除本地同名文件 -----------------------
       */
/*
String localPath = mmc.getDownloadPath();
String serverPath = mmc.getServerPath();
localPath = serverPath + localPath;
if (!localPath.endsWith("/")) {
localPath = localPath + "/";
}
localPath = localPath + tenantId + "/";
File localDir = new File(localPath);
if (!localDir.exists()) {
localDir.mkdirs();
}

*//**
   * --------------------- 获取远程路径及FTP连接参数 将FTP上的文件下载到本地 --------------------
   */
/*
String remotePath = mmc.getFtpFilePath();
if (!remotePath.endsWith("/")) {
remotePath = remotePath + "/";
}
String ftpIp = mmc.getFtpAdd();
String userName = mmc.getFtpUserName();
String password = mmc.getFtpPassword();

FTPClient fc = new FTPClient();
String[] ftpInfo = ftpIp.split(":");
try {
if (ftpInfo.length > 1) {
    fc.connect(ftpInfo[0], Integer.parseInt(ftpInfo[1]));
} else {
    fc.connect(ftpInfo[0]);
}

boolean login = fc.login(userName, password);
if (login) {
    String realPath = "";
    if ("/".equals(remotePath)) {
        realPath = tenantId + "/";
    } else {
        realPath = remotePath + tenantId + "/";
    }
    FTPFile[] ftpFiles = fc.listFiles(realPath);
    if (ftpFiles != null) {
        FileOutputStream fos = null;
        for (int i = 0; i < ftpFiles.length; i++) {
            FTPFile ftpFile = ftpFiles[i];
            String ftpFileName = ftpFile.getName();
            if (ftpFile.isFile()
                    && ftpFileName.equals(fileName)) {
                localFilePath = localPath + ftpFileName;
                fos = new FileOutputStream(localFilePath);
                fc.setFileType(FTPClient.BINARY_FILE_TYPE);
                fc.retrieveFile(realPath + ftpFileName, fos);
                fos.flush();
            }

            if (fos != null) {
                fos.close();
            }
        }

        File local = new File(localFilePath);
        if (local.exists()) {
            FileInputStream fis = null;
            OutputStream out = null;
            try {
                response.reset();
                response.setContentType("application/x-download");
                *//**
                   * 注意：此处对文件名重新编码非常重要，否则当文件名中有中文字符时， 可能出现一些难以理解的奇怪问题，例如显示无法打开internet站点、下载文件为未知类型等
                   */
/*
response.setHeader(
    "content-disposition",
    "attachment; filename="
            + new String(downloadName
                    .getBytes("gb2312"),
                    "iso8859-1"));

fis = new FileInputStream(local);
out = response.getOutputStream();
byte[] bs = new byte[1024];
int len = 0;
while ((len = fis.read(bs)) > 0) {
out.write(bs, 0, len);
}
out.flush();
} catch (Exception e) {
logger.error("FTP下载文件出错，" + e.toString(), e);
}

if (out != null) {
try {
out.close();
} catch (IOException e) {
e.printStackTrace();
}
}

if (fis != null) {
try {
fis.close();
} catch (IOException e) {
e.printStackTrace();
}
}

local.delete();

} else {
FtpUtil.returnMsg(response, "error");// 文件不存在
}
}

fc.disconnect();
}

} catch (Exception e) {
logger.error(e.toString());// 连接或读文件出错
}

} else {
FtpUtil.returnMsg(response, "error");// 查询配置出错(可能数据库问题)
}

}

*//**
   * 
   * 〈创建租户目录〉
   * 
   * @param mmc
   * @param tenantId
   * @return boolean
   */
/*
public static boolean createTenantDir(MultiMediaConfigModel mmc,
    String tenantId) {
boolean created = true;
String remotePath = mmc.getFtpFilePath();
if (!remotePath.endsWith("/")) {
    remotePath = remotePath + "/";
}
String ftpIp = mmc.getFtpAdd();
String userName = mmc.getFtpUserName();
String password = mmc.getFtpPassword();

FTPClient fc = new FTPClient();
String[] ftpInfo = ftpIp.split(":");
try {
    if (ftpInfo.length > 1) {
        fc.connect(ftpInfo[0], Integer.parseInt(ftpInfo[1]));
    } else {
        fc.connect(ftpInfo[0]);
    }

    boolean login = fc.login(userName, password);
    if (login) {
        String tenantDir = "";
        if ("/".equals(remotePath)) {
            tenantDir = tenantId;
        } else {
            tenantDir = remotePath + tenantId;
        }
        fc.makeDirectory(tenantDir);
    }
} catch (Exception e) {
    logger.error(e.toString());
    created = false;
}
return created;
}

*//**
   * --------------------------------------------------------------------- 〈从FTP下载指定文件到配置好的目录中,并把文件内容读到输出流中〉
   * 
   * @param mmc
   *            配置对象<该对象可通过getMultiMediaConfig(String configId)获得>
   * @param tenantId
   *            租户ID,系统资源按租户存放
   * @param fileName
   *            需要下载的文件
   * @param response
   *            response
   * @return void -------------------------------------------------------------- -------
   */
/*
public static void downloadToOutputStream(MultiMediaConfigModel mmc,
    String fileName, HttpServletResponse response) {
String localFilePath = "";
if (mmc != null) {
    *//**
       * ------------------------ 如果本地目录不存在，创建目录 如果存在，删除本地同名文件 -----------------------
       */
/*
String localPath = mmc.getDownloadPath();
String serverPath = mmc.getServerPath();
localPath = serverPath + localPath;
if (!localPath.endsWith("/")) {
localPath = localPath + "/";
}
// localPath = localPath + tenantId + "/";
File localDir = new File(localPath);
if (!localDir.exists()) {
localDir.mkdirs();
} else {
*//**
   * --------------------- 获取远程路径及FTP连接参数 将FTP上的文件下载到本地 --------------------
   *//*
    String remotePath = mmc.getFtpFilePath();
    if (!remotePath.endsWith("/")) {
    remotePath = remotePath + "/";
    }
    
    if ("/".equals(remotePath)) {
    remotePath = "";
    }
    String ftpIp = mmc.getFtpAdd();
    String userName = mmc.getFtpUserName();
    String password = mmc.getFtpPassword();
    
    FTPClient fc = new FTPClient();
    String[] ftpInfo = ftpIp.split(":");
    try {
    if (ftpInfo.length > 1) {
    fc.connect(ftpInfo[0], Integer.parseInt(ftpInfo[1]));
    } else {
    fc.connect(ftpInfo[0]);
    }
    
    boolean login = fc.login(userName, password);
    if (login) {
    FTPFile[] ftpFiles = fc.listFiles(remotePath);
    if (ftpFiles != null) {
    FileOutputStream fos = null;
    for (int i = 0; i < ftpFiles.length; i++) {
    FTPFile ftpFile = ftpFiles[i];
    String ftpFileName = ftpFile.getName();
    if (ftpFile.isFile()
          && ftpFile.getName().equals(fileName)) {
      localFilePath = localPath + ftpFileName;
      fos = new FileOutputStream(localFilePath);
      fc.setFileType(FTPClient.BINARY_FILE_TYPE);
      fc.retrieveFile(remotePath + ftpFileName,
              fos);
      fos.flush();
    }
    
    if (fos != null) {
      fos.close();
    }
    }
    }
    }
    
    fc.disconnect();
    } catch (Exception e) {
    logger.error(e.toString());
    }
    }
    
    File local = new File(localFilePath);
    {
    if (local.exists()) {
    FileInputStream fis = null;
    OutputStream out = null;
    try {
    response.setContentType("application/x-download");
    response.setHeader("content-disposition",
    "attachment; filename=" + fileName);
    fis = new FileInputStream(local);
    out = response.getOutputStream();
    byte[] buffer = new byte[1024];
    
    while (fis.read(buffer) > 0) {
    out.write(buffer);
    out.flush();
    }
    } catch (Exception e) {
    logger.error(e.toString());
    }
    
    }
    }
    }
    
    }
    
    public static void returnMsg(HttpServletResponse response, String msg) {
    if (msg == null || "".equals(msg)) {
    return;
    }
    Writer out = null;
    try {
    response.setCharacterEncoding("utf-8");
    out = response.getWriter();
    out.write(msg);
    
    } catch (IOException e) {
    e.printStackTrace();
    } finally {
    try {
    out.close();
    } catch (IOException e) {
    e.printStackTrace();
    }
    }
    }
    
    public static void main(String[] args) {
    MultiMediaConfigModel model=new MultiMediaConfigModel();
    model.setFtpFilePath("\\");
    model.setDownloadPath("D:\\");
    FtpUtil.downloadFromFTP(model, null, "a.txt");
    }
    }
    */