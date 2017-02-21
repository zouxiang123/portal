/**   
 * @Title: CopyUtil.java 
 * @Package com.xtt.platform.util
 * Copyright: Copyright (c) 2015
 * @author: bruce   
 * @date: 2016年3月17日 上午11:35:25 
 *
 */
package com.xtt.platform.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

public class CopyUtil {
    /**
     * 复制源对象和目标对象的属性值
     * 
     */
    public static void copy(Object source, Object target) throws Exception {

        Class sourceClass = source.getClass();// 得到对象的Class
        Class targetClass = target.getClass();// 得到对象的Class

        Field[] sourceFields = sourceClass.getDeclaredFields();// 得到Class对象的所有属性
        Field[] targetFields = targetClass.getDeclaredFields();// 得到Class对象的所有属性

        for (Field sourceField : sourceFields) {
            String name = sourceField.getName();// 属性名
            Class type = sourceField.getType();// 属性类型

            String methodName = name.substring(0, 1).toUpperCase() + name.substring(1);

            Method getMethod = sourceClass.getMethod("get" + methodName);// 得到属性对应get方法

            Object value = getMethod.invoke(source);// 执行源对象的get方法得到属性值

            for (Field targetField : targetFields) {
                String targetName = targetField.getName();// 目标对象的属性名

                if (targetName.equals(name)) {
                    Method setMethod = targetClass.getMethod("set" + methodName, type);// 属性对应的set方法

                    setMethod.invoke(target, value);// 执行目标对象的set方法
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Source source = new Source();
        source.setId(201);
        source.setName("小周");
        source.setCreateTime(new Date());

        Target target = new Target();

        copy(source, target);

        System.out.println(target.getId());
        System.out.println(target.getName());
        System.out.println(target.getCreateTime());
    }
}

class Source {
    private Integer id;
    private String name;
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}

class Target {
    private Integer id;
    private String name;
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
