/**   
 * @Title: CusotmizedPropertyUtil.java 
 * @Package com.xtt.platform.util.config 
 * @Description: 用一句话描述该文件做什么 
 * Copyright: Copyright (c) 2014 
 * Company:Tik team by iss
 * @author: Tik   
 * @date: 2014年5月19日 下午4:17:55 
 * @version: V1.0
 * update Release(文件修正记录)
 * <pre>
 * author--updateDate--description----------------------Flag————
 * Tik    2014-5-1    测试codesyle                      #Tik001
 *
 *
 *
 * </pre>
 *
 */
package com.xtt.platform.util.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class CusotmizedPropertyUtil extends PropertyPlaceholderConfigurer {

    private static Map<String, Object> ctxPropertiesMap = new HashMap<String, Object>();

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            ctxPropertiesMap.put(keyStr, value);
        }
    }

    public static Object getContextProperty(String name) {
        return ctxPropertiesMap.get(name);
    }

}
