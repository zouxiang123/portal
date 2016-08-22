package com.xtt.platform.util.time;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

public class DateUtil extends DateUtils {

	public DateUtil() {
		super();
	}

	/**
	 * 计算年龄
	 * 
	 * @Title: getAge
	 * @param brithday
	 * @return
	 *
	 */
	public static int getAge(Date brithday) {
		int age = 0;
		Calendar born = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		if (brithday != null) {
			now.setTime(new Date());
			born.setTime(brithday);
			if (born.after(now)) {
				throw new IllegalArgumentException("Can't be born in the future");
			}
			age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
			if (now.get(Calendar.DAY_OF_YEAR) < born.get(Calendar.DAY_OF_YEAR)) {
				age -= 1;
			}
		}
		return age;
	}
}
