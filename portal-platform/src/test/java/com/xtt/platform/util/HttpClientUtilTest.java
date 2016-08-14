/**   
 * @Title: HttpClientUtilTest.java 
 * @Package com.xtt.platform.util
 * Copyright: Copyright (c) 2015
 * @author: bruce   
 * @date: 2016年6月28日 下午7:50:28 
 *
 */
package com.xtt.platform.util;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.xtt.platform.util.http.HttpClientUtil;
import com.xtt.platform.util.io.JsonUtil;

public class HttpClientUtilTest {
	@Test
	public void testPostJson() {
		patient p = new patient();
		String json = new JsonUtil(null).toJson(p);
		String url = "http://127.0.0.1/pd/testJson.shtml";
		Map<String, String> map = new HashMap<String, String>();
		map.put("account", p.getAccount());
		map.put("name", p.getName());
		map.put("initial", p.getInitial());
		// System.out.println(HttpClientUtil.post(url, map));
		System.out.println(HttpClientUtil.postJson(url, json));
		// HttpClientUtil.postJson(url, json);
	}

	class patient {
		private String account = "123456";
		private String name = "张三";
		private String initial = "Z";

		public String getAccount() {
			return account;
		}

		public void setAccount(String account) {
			this.account = account;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getInitial() {
			return initial;
		}

		public void setInitial(String initial) {
			this.initial = initial;
		}

	}
}
