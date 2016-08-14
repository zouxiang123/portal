package com.xtt.platform.util;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.csvreader.CsvReader;
  
/*  
 * 文件规则  
 * Microsoft的格式是最简单的。以逗号分隔的值要么是“纯粹的”（仅仅包含在括号之前），  
 * 要么是在双引号之间（这时数据中的双引号以一对双引号表示）。  
 * Ten Thousand,10000, 2710 ,,"10,000","It's ""10 Grand"", baby",10K  
 * 这一行包含七个字段（fields）：  
 *  Ten Thousand  
 *  10000  
 *   2710   
 *  空字段  
 *  10,000  
 *  It's "10 Grand", baby  
 *  10K  
 * 每条记录占一行  
 * 以逗号为分隔符  
 * 逗号前后的空格会被忽略  
 * 字段中包含有逗号，该字段必须用双引号括起来。如果是全角的没有问题。  
 * 字段中包含有换行符，该字段必须用双引号括起来  
 * 字段前后包含有空格，该字段必须用双引号括起来  
 * 字段中的双引号用两个双引号表示  
 * 字段中如果有双引号，该字段必须用双引号括起来  
 * 第一条记录，可以是字段名  
 */  
  
public class CSVAnalysis {
    
    private static Logger log = Logger.getLogger(CSVAnalysis.class);

    public static List<String[]> readCsv(String fileName)
    {
        List<String[]> list = new ArrayList<String[]>();
        CsvReader reader = null;
        try
        {
            // 初始化CsvReader并指定列分隔符和字符编码
            reader = new CsvReader(fileName, ',', Charset.forName("UTF8"));
            while (reader.readRecord())
            {
                // 读取每行数据以数组形式返回
                String[] str = reader.getValues();
                if (str != null && str.length > 0)
                {
                    if (str[0] != null && !"".equals(str[0].trim()))
                    {
                        list.add(str);
                    }
                }
            }
        } catch (FileNotFoundException e)
        {
            log.error("Error reading csv file.", e);
        } catch (IOException e)
        {
            log.error("", e);
        }

        finally
        {
            if (reader != null)
                // 关闭CsvReader
                reader.close();
        }
        return list;
    }
    public static void main(String[] args) throws Throwable {   
        CSVAnalysis.readCsv("D:/report/date/group.csv");   
    }   
}  