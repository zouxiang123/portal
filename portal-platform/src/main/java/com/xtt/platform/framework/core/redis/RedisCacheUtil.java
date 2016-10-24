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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@SuppressWarnings({ "unchecked", "rawtypes" })
public class RedisCacheUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger("RedisLogger");

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
		try {
			redisTemplate.opsForValue().set(key, str);
		} catch (Exception e) {
			LOGGER.error("catch redis setString error", e);
			throw e;
		}
	}

	public static String getString(String key) {
		try {
			return (String) redisTemplate.opsForValue().get(key);
		} catch (Exception e) {
			LOGGER.error("catch redis getString error", e);
			throw e;
		}
	}

	/**
	 * 对象数据存储
	 * 
	 * @return
	 */
	public static void setObject(String key, Object obj) {
		setObject(key, obj, null, null);
	}

	/**
	 * 设置数据到redis
	 * 
	 * @Title: setObjectWithDB
	 * @param key
	 * @param obj
	 * @param dbIndex
	 *
	 */
	public static void setObjectWithDB(String key, Object obj, Integer dbIndex) {
		setObject(key, obj, dbIndex, null);
	}

	/**
	 * 设置数据到redis
	 * 
	 * @Title: setObjectWithDelay
	 * @param key
	 * @param obj
	 * @param liveTime(存活时间ms)
	 *
	 */
	public static void setObjectWithLiveTime(String key, Object obj, long liveTime) {
		setObject(key, obj, null, liveTime);
	}

	/**
	 * 设置数据到redis
	 * 
	 * @Title: setObject
	 * @param key
	 * @param obj
	 * @param dbIndex
	 * @param liveTime(存活时间ms)
	 *
	 */
	public static void setObject(String key, Object obj, Integer dbIndex, Long liveTime) {
		try {
			if (dbIndex == null) {
				redisTemplate.opsForValue().set(key, obj);
				if (liveTime != null)
					redisTemplate.expire(key, liveTime, TimeUnit.MILLISECONDS);
				return;
			}
			redisTemplate.execute(new RedisCallback<Object>() {
				@Override
				public Object doInRedis(RedisConnection connection) throws DataAccessException {
					connection.select(dbIndex);
					RedisSerializer keySerializer = redisTemplate.getKeySerializer();
					RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
					connection.set(keySerializer.serialize(key), valueSerializer.serialize(obj));
					if (liveTime != null)
						connection.pExpire(keySerializer.serialize(key), liveTime);
					return null;
				}
			}, true);
		} catch (Exception e) {
			LOGGER.error("catch redis setObject error", e);
			throw e;
		}
	}

	public static void batchSetObject(Map<String, ?> map) {
		batchSetObject(map, null, null);
	}

	public static void batchSetObjectWithDB(Map<String, ?> map, Integer dbIndex) {
		batchSetObject(map, dbIndex, null);
	}

	public static void batchSetObjectWithLiveTime(Map<String, ?> map, long liveTime) {
		batchSetObject(map, null, liveTime);
	}

	public static void batchSetObject(Map<String, ?> map, Integer dbIndex, Long liveTime) {
		try {
			final RedisSerializer keySerializer = redisTemplate.getKeySerializer();
			final RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
			redisTemplate.executePipelined(new RedisCallback<Object>() {
				@Override
				public Object doInRedis(RedisConnection connection) throws DataAccessException {
					if (dbIndex != null) {
						connection.select(dbIndex);
					}
					for (Entry<String, ?> entry : map.entrySet()) {
						connection.set(keySerializer.serialize(entry.getKey()), valueSerializer.serialize(entry.getValue()));
						if (liveTime != null) {
							connection.pExpire(keySerializer.serialize(entry.getKey()), liveTime);
						}
					}
					return null;
				}
			});
		} catch (Exception e) {
			LOGGER.error("catch redis batchSetObject error", e);
			throw e;
		}
	}

	public static Object getObject(String key) {
		try {
			return redisTemplate.opsForValue().get(key);
		} catch (Exception e) {
			LOGGER.error("catch redis getObject error", e);
			throw e;
		}
	}

	public static Object getObject(String key, Integer dbIndex) {
		try {
			return redisTemplate.execute(new RedisCallback<Object>() {
				@Override
				public Object doInRedis(RedisConnection connection) throws DataAccessException {
					if (dbIndex != null) {
						connection.select(dbIndex);
					}
					RedisSerializer keySerializer = redisTemplate.getKeySerializer();
					RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
					return valueSerializer.deserialize(connection.get(keySerializer.serialize(key)));
				}
			}, true);
		} catch (Exception e) {
			LOGGER.error("catch redis getObject with dbIndex error", e);
			throw e;
		}
	}

	public static List<?> batchGetObject(Collection<String> keys) {
		return batchGetObject(keys, null);
	}

	public static List<?> batchGetObject(Collection<String> keys, Integer dbIndex) {
		try {
			final RedisSerializer keySerializer = redisTemplate.getKeySerializer();
			List<Object> list = redisTemplate.executePipelined(new RedisCallback<Object>() {
				@Override
				public Object doInRedis(RedisConnection connection) throws DataAccessException {
					if (dbIndex != null) {
						connection.select(dbIndex);
					}
					for (String key : keys) {
						connection.get(keySerializer.serialize(key));
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
		} catch (Exception e) {
			LOGGER.error("catch redis batchGetObject error", e);
			throw e;
		}
	}

	/**
	 * List数据存储
	 * 
	 * @param key
	 * @param listValue
	 * @return
	 */
	public static void setList(String key, List<?> listValue) {
		try {
			redisTemplate.opsForList().leftPush(key, listValue);
		} catch (Exception e) {
			LOGGER.error("catch redis setList error", e);
			throw e;
		}
	}

	public static List<?> getList(String key) {
		try {
			return (List<?>) redisTemplate.opsForList().range(key, 0, -1);
		} catch (Exception e) {
			LOGGER.error("catch redis getList error", e);
			throw e;
		}
	}

	/**
	 * Map数据存储
	 * 
	 * @param key
	 * @param mapValue
	 * @return
	 */
	public static void setMap(String key, Map<Object, Object> mapValue) {
		try {
			redisTemplate.opsForHash().putAll(key, mapValue);
		} catch (Exception e) {
			LOGGER.error("catch redis setMap error", e);
			throw e;
		}
	}

	public static Map<Object, Object> getMap(String key) {
		try {
			return redisTemplate.opsForHash().entries(key);
		} catch (Exception e) {
			LOGGER.error("catch redis getMap error", e);
			throw e;
		}
	}

	/**
	 * Set数据存储
	 * 
	 * @param key
	 * @param setValue
	 * @return
	 */
	public static void setSet(String key, Set<?> setValue) {
		try {
			redisTemplate.opsForSet().add(key, setValue);
		} catch (Exception e) {
			LOGGER.error("catch redis setSet error", e);
			throw e;
		}
	}

	public static Set<?> getSet(String key) {
		try {
			return (Set<?>) redisTemplate.opsForSet().members(key);
		} catch (Exception e) {
			LOGGER.error("catch redis getSet error", e);
			throw e;
		}
	}

	/**
	 * 更新或者设置key的存活时间
	 * 
	 * @Title: setLiveTime
	 * @param key
	 * @param liveTime
	 *
	 */
	public static void setLiveTime(String key, Long liveTime) {
		redisTemplate.expire(key, liveTime, TimeUnit.MILLISECONDS);
	}

	/**
	 * 通过key删除
	 * 
	 * @param key
	 */
	public static void delete(String key) {
		deleteWithDB(key, null);
	}

	/**
	 * 通过key删除
	 * 
	 * @param key
	 */
	public static void deleteWithDB(String key, Integer dbIndex) {
		try {
			if (dbIndex == null) {
				redisTemplate.delete(key);
			} else {
				final RedisSerializer keySerializer = redisTemplate.getKeySerializer();
				redisTemplate.execute(new RedisCallback<Object>() {
					public Object doInRedis(RedisConnection connection) {
						connection.select(dbIndex);
						connection.del(keySerializer.serialize(key));
						return null;
					}
				}, true);
			}
		} catch (Exception e) {
			LOGGER.error("catch redis delete error", e);
			throw e;
		}
	}

	/**
	 * 通过keys 批量删除
	 * 
	 * @param key
	 */
	public static void delete(Collection<String> keys) {
		try {
			redisTemplate.delete(keys);
		} catch (Exception e) {
			LOGGER.error("catch redis batch delete error", e);
			throw e;
		}
	}

	/**
	 * 通过pattern模糊删除
	 * 
	 * @Title: deletePattern
	 * @param pattern
	 *
	 */
	public static void deletePattern(String pattern) {
		try {
			Set<String> keys = redisTemplate.keys(pattern);
			if (keys != null && !keys.isEmpty()) {
				delete(keys);
			}
		} catch (Exception e) {
			LOGGER.error("catch redis deletePattern error", e);
			throw e;
		}
	}

	public static Boolean flushDB() {
		try {
			return redisTemplate.execute(new RedisCallback<Boolean>() {
				@Override
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
					connection.flushDb();
					return true;
				}
			}, true);
		} catch (Exception e) {
			LOGGER.error("catch redis flushDB error", e);
			throw e;
		}
	}

	public static Long DBSize() {
		try {
			return redisTemplate.execute(new RedisCallback<Long>() {
				@Override
				public Long doInRedis(RedisConnection connection) throws DataAccessException {
					return connection.dbSize();
				}
			}, true);
		} catch (Exception e) {
			LOGGER.error("catch redis DBSize error", e);
			throw e;
		}
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
