/**   
 * @Title: RedisTest.java 
 * @Package com.xtt.platform.util
 * Copyright: Copyright (c) 2015
 * @author: bruce   
 * @date: 2016年10月20日 上午10:36:27 
 *
 */
package com.xtt.platform.util;

import java.util.HashMap;
import java.util.Map;

import com.xtt.platform.framework.core.redis.RedisCacheUtil;

public class RedisUtilTest extends BaseJunitTest {
	public void testBatchSetObject() throws InterruptedException {
		long millis = 2000l;
		Map<String, String> map = new HashMap<>();
		map.put("1", "1");
		map.put("2", "2");
		map.put("3", "3");
		RedisCacheUtil.batchSetObject(map, 2, millis);
		Thread.sleep(millis + 100);
		System.out.println(RedisCacheUtil.batchGetObject(map.keySet()).size());
	}
}
