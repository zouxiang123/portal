package com.xtt.platform.framework.core.redis;

import java.io.Serializable;

public class User implements Serializable {

    private String name;
    private int sex;

    public User(String name, int sex) {
        super();
        this.name = name;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User [name=" + name + ", sex=" + sex + "]";
    }

}
