/**   
 * @Title: PrimaryKeyUtil.java 
 * @Package com.xtt.platform.util
 * Copyright: Copyright (c) 2015
 * @author: bruce   
 * @date: 2016年6月16日 下午12:49:10 
 *
 */
package com.xtt.platform.util;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;

public class PrimaryKeyUtil {
    /** table对应的主键map */
    private static final ConcurrentHashMap<String, ConcurrentHashMap<String, Long>> KEY_MAP = new ConcurrentHashMap<String, ConcurrentHashMap<String, Long>>();
    /** 当前id和最大id之间的差值 */
    private static final int DIFF_COUNT = 0;
    /** 每次增加的count */
    private static final int ADD_COUNT = 100;

    /*static {
    	refresh();
    }
    */
    /** 根据table名称和租户id获取 */

    /*	public static synchronized Long getPrimaryKey(String tableName, Integer tenantId) {
    		long start = System.currentTimeMillis();
    		if (StringUtils.isBlank(tableName)) {
    			return null;
    		}
    		String id = (tenantId == null ? Constants.DEFAULT_SYS_TENANT_ID : tenantId) + tableName;
    		Long current = null;
    		try {
    			current = (Long) JdbcUtil.getSingle("select next_val from primary_key where id='" + id + "'");
    			JdbcUtil.update("update primary_key set next_val =next_val+1 where id='" + id + "'");
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    		System.out.println(System.currentTimeMillis() - start);
    		Map<String, Long> countMap = KEY_MAP.get(id);
    		if (countMap == null)// 不存在或者没维护时
    			return null;
    		long current = countMap.get("current");
    		// 更新当前id
    		countMap.put("current", current + 1);
    		if (countMap.get("max") - current <= DIFF_COUNT) {// 超出最大缓存安全值
    			updateCount(id, current, countMap);
    		}
    		return current;
    	}*/
    /**
     * 根据table名称和租户id获取主键id
     * 
     * @Title: getPrimaryKey
     * @param modelName
     * @param tenantId
     * @return primaryKey
     *
     */
    public static synchronized Long getPrimaryKey(String modelName, Integer tenantId) {
        return getPrimaryKey(modelName, tenantId, 1);
    }

    /**
     * 根据table名称和租户id获取主键id
     * 
     * @Title: getPrimaryKey
     * @param modelName
     * @param tenantId
     * @param count
     *            id的数目
     * @return 开始的那个id
     *
     */
    public static synchronized Long getPrimaryKey(String modelName, Integer tenantId, int count) {
        if (StringUtils.isBlank(modelName) || count < 0) {
            return null;
        }
        String id = (tenantId == null ? Constants.DEFAULT_SYS_TENANT_ID : tenantId) + modelName;
        Long current = null;
        try {
            current = (Long) DBUtil.getSingle("select getPrimaryKeyWithCount('" + id + "'," + count + ") from dual;");
            current = current - count + 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return current;
    }

    /** 更新来源总量 */
    private static void updateCount(String id, long start, Map<String, Long> countMap) {
        Object[] params = { start + ADD_COUNT, id };
        try {
            DBUtil.update("update primary_key set next_val =? where id=?", params);
            // 更新最大缓存Id
            countMap.put("max", start + ADD_COUNT);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /** 刷新KEY_MAP */
    public static synchronized void refresh() {
        try {
            List<Map<String, Object>> list = DBUtil.query("select id,next_val as count from primary_key;");
            KEY_MAP.clear();
            if (list != null && list.size() > 0) {
                ConcurrentHashMap<String, Long> currentMap;
                Long count;
                for (Map<String, Object> map : list) {
                    currentMap = new ConcurrentHashMap<String, Long>();
                    count = (Long) map.get("count");
                    if (count == null)
                        continue;
                    currentMap.put("current", count);
                    currentMap.put("max", count);
                    KEY_MAP.put((String) map.get("id"), currentMap);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
