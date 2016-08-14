package com.xtt.platform.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonUtil {

	private static Logger log = LoggerFactory.getLogger(CommonUtil.class);

	/**
	 * 返回Exception详细内容
	 * 
	 * @param e
	 *            Exception
	 * @return Exception详细内容
	 */
	public static String getExceptionMessage(Exception e) {
		if (e == null)
			return null;
		StackTraceElement[] ste = e.getStackTrace();
		StringBuffer exception = new StringBuffer();

		exception.append(e.toString());
		for (int i = 0; i < ste.length; i++) {
			exception.append("\n    at " + ste[i]);
		}
		return exception.toString();
	}

	/**
	 * json转对象
	 * 
	 * @param json
	 *            源Json字符串
	 * @param clazz
	 *            待转的对象
	 * @return
	 */
	public static <T> T json2Ojbect(String json, Class<T> clazz) {
		if (json == null)
			return null;

		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(json, clazz);
		} catch (Exception e) {
			log.error(getExceptionMessage(e));
			return null;
		}
	}

	/**
	 * 对象转Json
	 * 
	 * @param obj
	 *            待转对象
	 * @return
	 */
	public static String object2Json(Object obj) {
		if (obj == null)
			return null;

		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			log.error(getExceptionMessage(e));
			return null;
		}
	}
}
