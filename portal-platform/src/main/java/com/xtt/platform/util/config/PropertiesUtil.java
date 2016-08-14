package com.xtt.platform.util.config;

/** 
 * 读取配置文件
 */

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Tik
 * 
 */
public class PropertiesUtil {
	static Logger log =LoggerFactory.getLogger(PropertiesUtil.class);
	public PropertiesUtil() {
		
	}

	/**
	 * 读取配置文件 <per> ConfigUtil.getConfig("/spring/config.properties") return
	 * Properties prop
	 * 
	 * prop.getProperty("username").trim();
	 * 
	 * @param strfile
	 * @return
	 * 
	 */
	public static Properties loadProperties(String path) {
		Properties prop = new Properties();
		// String configFilePath = getDestFilePath(path);
		InputStream is = null;
		try {
			is = new FileInputStream(path);
			prop.load(is);
			is.close();
		} catch (Exception e) {
			log.info("loadProperties error: " + e.getMessage(), e);
		} finally {
			try {
				if (is != null) {
					is.close();
					is = null;
				}
			} catch (Exception e) {
			}
		}
		return prop;
	}

    /**
     * 读取配置文件 <per> ConfigUtil.getConfig("/spring/config.properties") return
     * Properties prop
     * 
     * prop.getProperty("username").trim();
     * 
     * @param strfile
     * @return
     * 
     */
    public static Properties loadJarProperties(String path) {
        Properties prop = new Properties();
        // String configFilePath = getDestFilePath(path);
        InputStream is = null;
        try {
            is = PropertiesUtil.class.getResourceAsStream(path);
            prop.load(is);
            is.close();
        } catch (Exception e) {
            log.info("loadProperties error: " + e.getMessage(), e);
        } finally {
            try {
                if (is != null) {
                    is.close();
                    is = null;
                }
            } catch (Exception e) {
            }
        }
        return prop;
    }

	/**
	 * 
	 * @Title: getProperty
	 * @author: Tik
	 * @CreateDate: 2014-4-2 下午1:54:21
	 * @UpdateUser: Tik
	 * @UpdateDate: 2014-4-2 下午1:54:21
	 * @UpdateRemark: 说明本次修改内容
	 * @Description: 根据key获取pop文件中的value
	 * @version: V1.0
	 * @param tagName
	 * @param porp
	 * @return
	 */
	public static String getProperty(String tagName, Properties porp) {
		try {
			return porp.getProperty(tagName).trim();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	*
	* @Title: getContextProperty
	* @Description: <p>获取config/下的config.properties文件属性值<p>
	* <pre>
		getContextProperty("user.name");
	* </pre>
	* @param: <p>@param key
	* @param: <p>@return<p>
	* @date: 2014年5月16日
	* @return: Object
	* @throws 
	*
	 */
	public static Object getContextProperty(String key) {  
        String default_Path = "/config/config.properties";
		return getProperty(key,loadJarProperties(default_Path));
    } 
	
	private InputStream getPropsIS(String propertiesPath) {
		InputStream ins = this.getClass().getResourceAsStream(propertiesPath);
		return ins;
	}

	/**
	 * 读取属性文件中的属性值
	 * 
	 * @param key
	 * @return value
	 */
	public String readSingleProps(String key,String propertiesPath) {
		String retValue = "";
		Properties props = new Properties();
		try {
			/*
			 * if (!FileUtil.isFileExist(getPropsFilePath())) { return ""; }
			 * FileInputStream fi = new FileInputStream(getPropsFilePath());
			 */
			InputStream fi = getPropsIS(propertiesPath);
			props.load(fi);
			fi.close();

			retValue = props.getProperty(key);
		} catch (Exception e) {
			return "";
		}
		return retValue;
	}

	/**
	 * 读取属性文件中的属性值
	 * 
	 * @return
	 */
	public HashMap readAllProps(String propertiesPath) {
		HashMap h = new HashMap();
		Properties props = new Properties();
		try {
			/*
			 * if (!FileUtil.isFileExist(getPropsFilePath())) return new
			 * HashMap(); FileInputStream fi = new
			 * FileInputStream(getPropsFilePath());
			 */
			InputStream fi = getPropsIS(propertiesPath);
			props.load(fi);
			fi.close();
			Enumeration er = props.propertyNames();
			while (er.hasMoreElements()) {
				String paramName = (String) er.nextElement();
				h.put(paramName, props.getProperty(paramName));
			}
		} catch (Exception e) {
			return new HashMap();
		}
		return h;
	}

}
