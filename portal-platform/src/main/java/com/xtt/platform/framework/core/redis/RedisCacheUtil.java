package com.xtt.platform.framework.core.redis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * redis远程远程操作 存储返回 true 成功 false失败
 * 
 * @author wolf.yansl
 */
public class RedisCacheUtil {

	private static RedisTemplate<String, Object> redisTemplate;

	/*	static{
			redisTemplate = (RedisTemplate)SpringUtil.getBean("redisCache");
		}*/

	/**
	 * 字符串数据存储
	 * 
	 * @param key
	 * @param str
	 * @return
	 */
	public static void setString(String key, String str) {
		redisTemplate.opsForValue().set(key, str);
	}

	public static String getString(String key) {
		return (String) redisTemplate.opsForValue().get(key);
	}

	/**
	 * 对象数据存储
	 * 
	 * @param key
	 * @param obj
	 * @return
	 */
	public static void setObject(String key, Object obj) {
		redisTemplate.opsForValue().set(key, obj);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void batchSetObject(Map<String, ?> map) {
		final RedisSerializer keySerializer = redisTemplate.getKeySerializer();
		final RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
		redisTemplate.executePipelined(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				for (Entry<String, ?> entry : map.entrySet()) {
					connection.set(keySerializer.serialize(entry.getKey()), valueSerializer.serialize(entry.getValue()));
				}
				return null;
			}
		});
	}

	public static Object getObject(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<?> batchGetObject(Collection<String> keys) {
		final RedisSerializer keySerializer = redisTemplate.getKeySerializer();
		List<Object> list = redisTemplate.executePipelined(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				for (String key : keys) {
					connection.get(keySerializer.serialize(key));
					// connection.rPop(keySerializer.serialize(key));
				}
				return null;
			}
		}, redisTemplate.getValueSerializer());
		if (list != null && !list.isEmpty()) {
			for (Iterator<Object> it = list.iterator(); it.hasNext();) {
				if (it.next() == null)
					it.remove();
			}
		}
		return list;
	}

	/**
	 * List数据存储
	 * 
	 * @param key
	 * @param listValue
	 * @return
	 */
	public static void setList(String key, List<?> listValue) {
		redisTemplate.opsForList().leftPush(key, listValue);
	}

	public static List<?> getList(String key) {
		return (List<?>) redisTemplate.opsForList().range(key, 0, -1);
	}

	/**
	 * Map数据存储
	 * 
	 * @param key
	 * @param mapValue
	 * @return
	 */
	public static void setMap(String key, Map<Object, Object> mapValue) {
		redisTemplate.opsForHash().putAll(key, mapValue);
	}

	public static Map<Object, Object> getMap(String key) {
		return redisTemplate.opsForHash().entries(key);
	}

	/**
	 * Set数据存储
	 * 
	 * @param key
	 * @param setValue
	 * @return
	 */
	public static void setSet(String key, Set<?> setValue) {
		redisTemplate.opsForSet().add(key, setValue);
	}

	public static Set<?> getSet(String key) {
		return (Set<?>) redisTemplate.opsForSet().members(key);
	}

	/**
	 * 通过key删除
	 * 
	 * @param key
	 */
	public static void delete(String key) {
		redisTemplate.delete(key);
	}

	/**
	 * 通过keys 批量删除
	 * 
	 * @param key
	 */
	public static void delete(Collection<String> keys) {
		redisTemplate.delete(keys);
	}

	/**
	 * 通过pattern模糊删除
	 * 
	 * @Title: deletePattern
	 * @param pattern
	 *
	 */
	public static void deletePattern(String pattern) {
		Set<String> keys = redisTemplate.keys(pattern);
		if (keys != null && !keys.isEmpty()) {
			delete(keys);
		}
	}

	public static Boolean flushDB() {
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				connection.flushDb();
				return true;
			}
		});
	}

	public static Long DBSize() {
		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.dbSize();
			}
		});
	}

	public static RedisTemplate<String, Object> getRedisTemplate() {
		return redisTemplate;
	}

	public static void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		RedisCacheUtil.redisTemplate = redisTemplate;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
		@SuppressWarnings({ "unused", "resource" })
		ApplicationContext factory = new ClassPathXmlApplicationContext("config/springRedis.xml");
		/*System.out.println(factory.getType("redisCache"));*/
		// RedisCacheUtil cache = (RedisCacheUtil) factory.getBean("redisCache");

		/*测试String*/
		RedisCacheUtil.setString("demoString1", "demoStringValue1");
		RedisCacheUtil.setString("demoString2", "demoStringValue2");
		System.out.println(RedisCacheUtil.getString("demoString1"));

		// 测试Object
		RedisCacheUtil.setObject("demoObject1", new String("demoObject1"));
		RedisCacheUtil.setObject("demoObject2", new HashMap<String, String>().put("hashKey", "hashvalue"));
		RedisCacheUtil.setObject("demoObject3", new User("uDemo", 100));
		System.out.println(RedisCacheUtil.getObject("demoObject1"));
		System.out.println(RedisCacheUtil.getObject("demoObject2"));
		System.out.println(RedisCacheUtil.getObject("demoObject3"));

		// 测试List 不能持久保存
		List list1 = new ArrayList();
		list1.add(1);
		list1.add("String");
		list1.add(new HashMap().put("key", "value"));
		list1.add(new User("ulist", 500));
		RedisCacheUtil.setList("demoList1", list1);
		System.out.println(RedisCacheUtil.getList("demoList1"));

		/*测试Map*/
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("key1", "ni");
		map.put("key2", new User("map", 1000));
		RedisCacheUtil.setMap("demoMap1", map);
		System.out.println(RedisCacheUtil.getMap("demoMap1"));

		/*测试Set 不能持久保存*/
		Set set1 = new HashSet();
		set1.add(1);
		set1.add("String");
		set1.add(new HashMap().put("key", "value"));
		set1.add(new User("uSET", 500));
		RedisCacheUtil.setSet("demoSet1", set1);
		System.out.println(RedisCacheUtil.getSet("demoSet1"));

		RedisTemplate<String, Object> temp = RedisCacheUtil.getRedisTemplate();
		RedisCacheUtil.setString("11111111", "22222222222");
		temp.expire("test", 1, TimeUnit.SECONDS);
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println(RedisCacheUtil.getString("11111111"));
	}
}
