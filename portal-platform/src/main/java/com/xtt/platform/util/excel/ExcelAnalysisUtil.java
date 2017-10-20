/**   
 * @Title: ExcelAnalysis.java 
 * @Package com.xtt.qc.common.utils
 * Copyright: Copyright (c) 2015
 * @author: wolf.yansl 
 * @date: 2016年12月14日 下午3:10:12 
 *
 */
package com.xtt.platform.util.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 解析excel文件内容
 * 
 * @ClassName: ExcelAnalysisUtil
 * @date: 2016年12月14日 下午3:12:43
 * @version: V1.0
 */
public class ExcelAnalysisUtil {
    /**
     * 获取某列前缀字符
     */
    public static String CELL_PREFIX = "cell";

    /**
     * 获取excel每行数据
     * 
     * @Title: getSheetRows
     * @param excelInputStream
     *            excel文件流
     * @param sheetIndex
     *            excel工作表 0 表示解析第一个工作表，n可以指定工作表，默认从第一行读取数据
     * @return
     * @throws Exception
     */
    public static List<Map<String, String>> getSheetRows(InputStream excelInputStream, int sheetIndex) throws Exception {
        return getRowData(excelInputStream, sheetIndex, 0);
    }

    /**
     * 获取excel每行数据
     * 
     * @Title: getSheetRows
     * @param excelInputStream
     *            excel文件流
     * @param sheetIndex
     *            excel工作表 0 表示解析第一个工作表，n可以指定工作表
     * @param titleRowNum
     *            excel指定标题行数：从标题行读取，请输入行数,默认从0开始计算
     * @return
     * @throws Exception
     */
    public static List<Map<String, String>> getSheetRows(InputStream excelInputStream, int sheetIndex, int titleRowNum) throws Exception {
        return getRowData(excelInputStream, sheetIndex, titleRowNum);
    }

    /**
     * 获取excel每行数据
     * 
     * @Title: getSheetRows
     * @param file
     *            excel文件
     * @param sheetIndex
     *            excel工作表 0 表示解析第一个工作表，n可以指定工作表，默认读取第一行数据
     * @return
     * @throws Exception
     *
     */
    public static List<Map<String, String>> getSheetRows(File file, int sheetIndex) throws Exception {
        return getSheetRows(new FileInputStream(file), sheetIndex, 0);
    }

    /**
     * 获取excel每行数据
     * 
     * @Title: getSheetRows
     * @param file
     *            excel文件
     * @param sheetIndex
     *            excel工作表 0 表示解析第一个工作表，n可以指定工作表，默认读取第一行数据
     * @param titleRowNum
     *            excel标题行数，默认从0行开始计算
     * @return
     * @throws Exception
     *
     */
    public static List<Map<String, String>> getSheetRows(File file, int sheetIndex, int titleRowNum) throws Exception {
        return getSheetRows(new FileInputStream(file), sheetIndex, titleRowNum);
    }

    /**
     * 读取excel数据
     * 
     * @Title: getRowData
     * @param excelInputStream
     *            excel文件
     * @param sheetIndex
     *            工作表 表下表
     * @param titleRowNum
     *            标题行行数
     * @return
     * @throws Exception
     *
     */
    private static List<Map<String, String>> getRowData(InputStream excelInputStream, int sheetIndex, int titleRowNum) throws Exception {
        List<Map<String, String>> resultRows = new ArrayList<Map<String, String>>();
        Map<Integer, String> titleMap = new HashMap<Integer, String>();
        Workbook excelBook = openExcelFile(excelInputStream);

        Sheet excelSheet = getSheet(excelBook, sheetIndex);
        int maxRowNum = excelSheet.getLastRowNum();
        if (maxRowNum <= 0 || titleRowNum >= maxRowNum) {
            return resultRows;
        }

        // 读取起始行
        int startRowNum = titleRowNum;

        // 获取标题
        getRowTitle(titleMap, excelSheet, startRowNum);

        // 读取数据行
        startRowNum++;

        // 获取所有数据行
        for (int i = startRowNum; i <= maxRowNum; i++) {
            Row row = excelSheet.getRow(i);
            Map<String, String> cellMap = getRowCellData(row, titleMap);
            resultRows.add(cellMap);
        }
        return resultRows;
    }

    /**
     * 获取所有sheet中的数据
     * 
     * @Title: getSheetsRowData
     * @param excelInputStream
     *            excel文件流
     * @param sheetIndex
     *            读取几个sheet 默认从0开始
     * @param titleRowNum
     *            从第几行开始读取
     * @return
     * @throws Exception
     *
     */
    public static Map<Integer, List<Map<String, String>>> getSheetAll(InputStream excelInputStream, int sheetIndex, int titleRowNum)
                    throws Exception {
        Map<Integer, List<Map<String, String>>> resultSheets = new HashMap<Integer, List<Map<String, String>>>();

        Workbook excelBook = openExcelFile(excelInputStream);

        for (int si = 0; si <= sheetIndex; si++) {
            List<Map<String, String>> resultRows = new ArrayList<Map<String, String>>();
            Map<Integer, String> titleMap = new HashMap<Integer, String>();
            Sheet excelSheet = getSheet(excelBook, si);
            int maxRowNum = excelSheet.getLastRowNum();
            if (maxRowNum <= 0 || titleRowNum >= maxRowNum) {
                continue;
            }

            // 读取起始行
            int startRowNum = titleRowNum;

            // 获取标题
            getRowTitle(titleMap, excelSheet, startRowNum);

            // 读取数据行
            startRowNum++;

            // 获取所有数据行
            for (int i = startRowNum; i <= maxRowNum; i++) {
                Row row = excelSheet.getRow(i);
                Map<String, String> cellMap = getRowCellData(row, titleMap);
                resultRows.add(cellMap);
            }
            resultSheets.put(si, resultRows);
        }

        return resultSheets;
    }

    /**
     * 获取标题行
     * 
     * @Title: getRowTitle
     * @param titleMap
     * @param excelSheet
     *
     */
    private static void getRowTitle(Map<Integer, String> titleMap, Sheet excelSheet, int startRowNum) {
        Row titleRow = excelSheet.getRow(startRowNum);
        // 获取所有列
        int cellNum = titleRow.getLastCellNum();
        for (int c = 0; c <= cellNum; c++) {
            Cell cell = titleRow.getCell(c);
            if (cell != null) {
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                titleMap.put(c, cell.getStringCellValue());
            } else {
                titleMap.put(c, "");
            }
        }
    }

    /**
     * 获取所有列内容
     * 
     * @Title: getRowCellData
     * @param row
     * @return
     *
     */
    private static Map<String, String> getRowCellData(Row row, Map<Integer, String> titleMap) {
        // 获取所有列
        int cellNum = row.getLastCellNum();
        Map<String, String> cellMap = new HashMap<String, String>();
        for (int c = 0; c <= cellNum; c++) {
            Cell cell = row.getCell(c);
            if (cell != null) {
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cellMap.put(titleMap.get(c), cell.getStringCellValue());
            }
        }
        return cellMap;
    }

    /**
     * 获取excel工作簿
     * 
     * @Title: openExcelFile
     * @param f
     * @param fileExt
     * @return
     * @throws Exception
     */
    private static Workbook openExcelFile(InputStream excelInputStream) throws Exception {
        return WorkbookFactory.create(excelInputStream);
    }

    /**
     * 获取excel工作表
     * 
     * @Title: getRequiredSheet
     * @param workbook
     * @param sheetIndex
     * @return
     *
     */
    private static Sheet getSheet(Workbook workbook, int sheetIndex) {
        return workbook.getSheetAt(sheetIndex);
    }

    public static void main(String[] args) throws Exception {
        File file = new File("c:/33.xls");
        List<Map<String, String>> resultExcelMap = getSheetRows(new FileInputStream(file), 0);
        for (Map<String, String> rowMap : resultExcelMap) {
            System.out.println("编号:" + rowMap.get("编号"));
            System.out.println("名称:" + rowMap.get("名称"));
            System.out.println("性别:" + rowMap.get("性别"));
            System.out.println("出生日期:" + rowMap.get("出生日期"));
            System.out.println("身份证号:" + rowMap.get("身份证号"));

            System.out.println("--------------------------------------");
        }

    }

}
