package com.xtt.platform.util.lang;

import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomUtil extends RandomStringUtils {
	/**
	 * 得到一个UUID
	 * 
	 * @return
	 */
	public static String getUUID() {
		return StringUtil.replace("-", "", UUID.randomUUID().toString());
	}


    /**
     * 生成一个指定位数的随机数
     * @param digits 位数
     * @return
     */
    public static long getRandomNum(int digits) {
        long seccode = (long) (Math.random() * 9 * Math.pow(10, digits - 1) + Math.pow(10, digits - 1));
        return seccode;
    }


}
