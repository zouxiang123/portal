/**   
 * @Title: BeanUtil.java 
 * @Package com.xtt.platform.util
 * Copyright: Copyright (c) 2015
 * @author: bruce   
 * @date: 2017年3月15日 上午9:53:29 
 *
 */
package com.xtt.platform.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

public class BeanUtil extends BeanUtils {
    /**
     * 自定义映射关系copy对象<br>
     * 映射关系Map key：sourceProperties,value:targetProperties
     * 
     * @Title: copyProperties
     * @param source
     *            来源对象
     * @param target
     *            目标对象
     * @param sourceToTargetMap
     *            映射关系
     *
     */
    public static void copyProperties(Object source, Object target, Map<String, String> sourceToTargetMap) {
        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");
        Assert.notEmpty(sourceToTargetMap);
        Class<?> sourceClass = source.getClass();
        Class<?> targetClass = target.getClass();
        sourceToTargetMap.forEach((form, to) -> {
            PropertyDescriptor sourcePd = getPropertyDescriptor(sourceClass, form);
            PropertyDescriptor targetPd = getPropertyDescriptor(targetClass, to);
            if (sourcePd != null && targetPd != null) {
                Method readMethod = sourcePd.getReadMethod();
                Method writeMethod = targetPd.getWriteMethod();
                if (readMethod != null && ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                    try {
                        if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                            readMethod.setAccessible(true);
                        }
                        Object value = readMethod.invoke(source);
                        if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                            writeMethod.setAccessible(true);
                        }
                        writeMethod.invoke(target, value);
                    } catch (Throwable ex) {
                        throw new FatalBeanException("Could not copy property '" + targetPd.getName() + "' from source to target", ex);
                    }
                }
            }
        });
    }
}
