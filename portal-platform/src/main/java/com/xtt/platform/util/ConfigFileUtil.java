package com.xtt.platform.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigFileUtil {

    /**
     * 读取Properties文件
     * 
     * @param filePath
     *            文件路径
     * @return properties 文件类
     */
    public Properties readProperty(String filePath) {
        Properties prop = new Properties();
        Resource file = null;
        InputStream propertiesInputStream = null;
        try {
            file = new ClassPathResource(filePath);
            propertiesInputStream = file.getInputStream();
            prop.load(propertiesInputStream);
        } catch (Exception e) {
            System.err.println("读取Properties文件失败！" + e);
        } finally {
            try {
                propertiesInputStream.close();
            } catch (IOException e) {
                System.err.println("关闭流失败！" + e);
            }
        }
        return prop;
    }
}
