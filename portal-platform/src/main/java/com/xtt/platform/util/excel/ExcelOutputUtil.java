/**   
 * @Title: ExcelOutputUtil.java 
 * @Package com.xtt.qc.common.utils
 * Copyright: Copyright (c) 2015
 * @author: abc   
 * @date: 2016年12月22日 上午8:26:55 
 *
 */
package com.xtt.platform.util.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

public class ExcelOutputUtil {

    /**
     * 将Excel文件生成到指定目录中
     * 
     * @Title: outputLocation
     * @param srcFile
     *            excel模板文件
     * @param excelData
     *            导出excel数据
     * @param discFile
     *            生成的目录位置
     * @param request
     *            请求request对象
     * @return
     *
     */
    public static boolean outputLocation(String srcFile, Map<String, Object> excelData, String discFile, HttpServletRequest request) {
        try {
            output(excelTemplatePath(request, srcFile), excelData, discFile);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 下载Excel
     * 
     * @Title: outputDownload
     * @param srcFile
     *            excel模板文件
     * @param excelData
     *            excel数据
     * @param exportFileName
     *            下载文件名称
     * @param response
     *            请求response对象
     * @param request
     *            请求request对象
     * @return
     *
     */
    public static boolean outputDownload(String srcFile, Map<String, Object> excelData, String exportFileName, HttpServletRequest request,
                    HttpServletResponse response) {
        try {
            Workbook wordbook = outputWorkBook(excelTemplatePath(request, srcFile), excelData);
            exportWorkbook(wordbook, exportFileName, response);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 生成到指定文件中
     * 
     * @Title: output
     * @param srcFile
     *            excel模板文件
     * @Title: output 导出excel数据
     * @param excelData
     *            生成文件位置
     * @throws IOException
     * @throws InvalidFormatException
     * @throws ParsePropertyException
     *
     */
    private static void output(String srcFile, Map<String, Object> excelData, String discFile)
                    throws ParsePropertyException, InvalidFormatException, IOException {
        XLSTransformer transformer = new XLSTransformer();
        transformer.transformXLS(srcFile, excelData, discFile);
    }

    /**
     * 返回已经生成好的工作Excel工作簿对象
     * 
     * @Title: output
     * @param srcFile
     *            excel模板文件
     * @param excelData
     *            导出excel数据
     * @return
     * @throws ParsePropertyException
     * @throws InvalidFormatException
     * @throws IOException
     *
     */
    private static Workbook outputWorkBook(String srcFile, Map<String, Object> excelData)
                    throws ParsePropertyException, InvalidFormatException, IOException {
        XLSTransformer transformer = new XLSTransformer();
        return transformer.transformXLS(new FileInputStream(new File(srcFile)), excelData);
    }

    /**
     * 弹出excel文件，提供下载信息
     * 
     * @param hssWorkbook
     *            工作簿
     * @param xlsTemplateName
     *            下载的工作簿名称
     * @param response
     * @throws Exception
     */
    private static void exportWorkbook(Workbook hssWorkbook, String exportfileName, HttpServletResponse response) throws Exception {
        // 设置导出弹出框，以及下载文件名称
        exportfileName = java.net.URLEncoder.encode(exportfileName, "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + exportfileName + ".xls");
        OutputStream os = response.getOutputStream();
        hssWorkbook.write(os);
        os.flush();
        os.close();

    }

    /**
     * 
     * @Title: excelTemplatePath
     * @param request
     * @param excelTemplate
     *            excel模板
     * @return
     *
     */
    private static String excelTemplatePath(HttpServletRequest request, String excelTemplateName) {
        String srcFile = request.getSession().getServletContext().getRealPath(excelTemplateName);
        return srcFile;
    }

}
