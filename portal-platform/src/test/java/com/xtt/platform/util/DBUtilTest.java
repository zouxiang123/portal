/**   
 * @Title: JdbcUtilTest.java 
 * @Package com.lt.platform.util
 * Copyright: Copyright (c) 2015
 * @author: bruce   
 * @date: 2016年6月16日 下午2:17:08 
 *
 */
package com.xtt.platform.util;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class DBUtilTest extends BaseJunitTest {
	public void testSingleQuery() throws SQLException {

		List<Map<String, Object>> list = DBUtil
						.query("select TABLE_NAME as name from information_schema.tables where table_schema='test' AND TABLE_TYPE ='BASE TABLE'");
		if (list != null && list.size() > 0) {
			for (Map<String, Object> map : list) {
				List<Map<String, Object>> temList = DBUtil.query("SHOW FIELDS FROM " + (String) map.get("name"));
				boolean exists = false;
				for (Map<String, Object> t : temList) {
					for (String str : t.keySet()) {
						if ("field".equalsIgnoreCase(str) && "fk_tenant_id".equalsIgnoreCase((String) t.get(str))) {
							exists = true;
							break;
						}
					}
				}
				if (!exists) {
					System.out.println((String) map.get("name"));
				}
			}
		}
	}

	public void testSingle() throws SQLException {
		for (int i = 0; i < 100; i++) {
			String[] params = { "10101Test" };
			System.out.println((Long) DBUtil.getSingle("select next_val from primary_key where id=?", params));
		}
	}
}