package com.xtt.platform.util.model;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xtt.platform.framework.exception.RDPException;

/**
 * 
 * @ClassName: ModelUtil
 * @description: 对象转换
 * @author: Tik
 * @date: 2014年5月28日 上午9:55:27
 * @version: V1.0
 *
 */
public class ModelUtil {

    public ModelUtil() {

    }

    /**
     * pojo转换成HashMap
     *
     * @Title: ConvertObjToMap @Description: <p>这里用一句话描述这个方法的作用<p> <pre> 这里描述这个方法的使用方法 – 可选 </pre> @param: <p>@param obj @param: <p>@return<p> @date:
     * 2014年5月27日 @return: Map<String,Object> @throws
     *
     */
    public static Map<String, Object> ConvertPojoToMap(Object obj) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Method[] m = obj.getClass().getMethods();
            for (int i = 0; i < m.length; i++) {
                String method = m[i].getName();
                if (method.startsWith("get")) {
                    Object value = m[i].invoke(obj);
                    String key = method.substring(3);
                    key = key.substring(0, 1).toLowerCase() + key.substring(1);
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

}
