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

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationUtils;
import org.springframework.util.Assert;

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
	 * @Title: setObjectWithTimeout
	 * @param key
	 * @param obj
	 * @param timeout(存活时间ms)
	 *
	 */
	public static void setObjectWithTimeout(String key, Object obj, long timeout) {
		setObject(key, obj, null, timeout);
	}

	/**
	 * 设置数据到redis
	 * 
	 * @Title: setObject
	 * @param key
	 * @param obj
	 * @param dbIndex
	 * @param timeout(存活时间ms)
	 *
	 */
	public static void setObject(String key, Object obj, Integer dbIndex, Long timeout) {
		try {
			redisTemplate.execute(new RedisCallback<Object>() {
				@Override
				public Object doInRedis(RedisConnection connection) throws DataAccessException {
					if (dbIndex != null)
						connection.select(dbIndex);
					byte[] rawKey = serializeKey(key, redisTemplate.getKeySerializer());
					connection.set(rawKey, serializeKey(obj, redisTemplate.getValueSerializer()));
					if (timeout != null)
						connection.pExpire(rawKey, timeout);
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

	public static void batchSetObjectWithTimeout(Map<String, ?> map, long timeout) {
		batchSetObject(map, null, timeout);
	}

	public static void batchSetObject(Map<String, ?> map, Integer dbIndex, Long timeout) {
		try {
			final RedisSerializer keySerializer = redisTemplate.getKeySerializer();
			final RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
			redisTemplate.executePipelined(new RedisCallback<Object>() {
				@Override
				public Object doInRedis(RedisConnection connection) throws DataAccessException {
					if (dbIndex != null) {
						connection.select(dbIndex);
					}
					Map<byte[], byte[]> rawMap = new HashMap<>();
					for (Entry<String, ?> entry : map.entrySet()) {
						rawMap.put(keySerializer.serialize(entry.getKey()), valueSerializer.serialize(entry.getValue()));
					}
					connection.mSet(rawMap);
					if (timeout != null) {
						for (byte[] rawKey : rawMap.keySet()) {
							connection.pExpire(rawKey, timeout);
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
			final RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
			List<Object> list = redisTemplate.execute(new RedisCallback<List<Object>>() {
				@Override
				public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
					if (dbIndex != null) {
						connection.select(dbIndex);
					}
					return SerializationUtils.deserialize(connection.mGet(serializeKeys(keys, redisTemplate.getKeySerializer())), valueSerializer);
				}
			});
			removeNullValue(list);
			return list;
		} catch (Exception e) {
			LOGGER.error("catch redis batchGetObject error", e);
			throw e;
		}
	}

	/**
	 * 根据key模糊查询
	 * 
	 * @Title: getByPattern
	 * @param pattern
	 * @return
	 *
	 */
	public static List<?> getByPattern(String pattern) {
		return getByPattern(pattern, null);
	}

	/**
	 * 根据key模糊查询
	 * 
	 * @Title: getByPattern
	 * @param pattern
	 * @param dbIndex
	 * @return
	 *
	 */
	public static List<?> getByPattern(String pattern, Integer dbIndex) {
		try {
			final RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
			return (List<Object>) redisTemplate.execute(new SessionCallback() {
				@Override
				public List<Object> execute(RedisOperations operations) throws DataAccessException {
					Set<byte[]> rawKeys = redisTemplate.execute(new RedisCallback<Set<byte[]>>() {

						public Set<byte[]> doInRedis(RedisConnection connection) {
							if (dbIndex != null) {
								connection.select(dbIndex);
							}
							return connection.keys(serializeKey(pattern, redisTemplate.getKeySerializer()));
						}
					}, true);

					if (null != rawKeys && rawKeys.size() > 0) {
						List<Object> values = (List<Object>) operations.execute(new RedisCallback<List<Object>>() {
							@Override
							public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
								if (dbIndex != null) {
									connection.select(dbIndex);
								}
								return SerializationUtils.deserialize(connection.mGet(rawKeys.toArray(new byte[rawKeys.size()][])), valueSerializer);
							}
						});
						removeNullValue(values);
						return values;
					}
					return null;
				}
			});
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
	 * @Title: setTimeout
	 * @param key
	 * @param timeout
	 *
	 */
	public static void setTimeout(String key, Long timeout) {
		setTimeout(key, timeout, null);
	}

	/**
	 * 更新或者设置key的存活时间
	 * 
	 * @Title: setTimeout
	 * @param key
	 * @param timeout
	 * @param dbIndex
	 *
	 */
	public static boolean setTimeout(String key, Long timeout, Integer dbIndex) {
		return redisTemplate.execute(new RedisCallback<Boolean>() {

			public Boolean doInRedis(RedisConnection connection) {
				if (dbIndex != null) {
					connection.select(dbIndex);
				}
				byte[] rawKey = serializeKey(key, redisTemplate.getKeySerializer());
				try {
					return connection.pExpire(rawKey, timeout);
				} catch (Exception e) {
					// Driver may not support pExpire or we may be running on Redis 2.4
					return connection.expire(rawKey, TimeoutUtils.toSeconds(timeout, TimeUnit.MILLISECONDS));
				}
			}
		}, true);
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
			redisTemplate.execute(new RedisCallback<Object>() {
				public Object doInRedis(RedisConnection connection) {
					if (dbIndex != null) {
						connection.select(dbIndex);
					}
					connection.del(serializeKey(key, redisTemplate.getKeySerializer()));
					return null;
				}
			}, true);
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
			delete(keys, null);
		} catch (Exception e) {
			LOGGER.error("catch redis batch delete error", e);
			throw e;
		}
	}

	/**
	 * 通过keys 批量删除
	 * 
	 * @param key
	 */
	public static boolean delete(Collection<String> keys, Integer dbIndex) {
		try {
			if (CollectionUtils.isEmpty(keys)) {
				return true;
			}
			final byte[][] rawKeys = serializeKeys(keys, redisTemplate.getKeySerializer());
			redisTemplate.execute(new RedisCallback<Boolean>() {
				public Boolean doInRedis(RedisConnection connection) {
					if (dbIndex != null) {
						connection.select(dbIndex);
					}
					connection.del(rawKeys);
					return true;
				}
			}, true);
		} catch (Exception e) {
			LOGGER.error("catch redis batch delete error", e);
			throw e;
		}
		return false;
	}

	/**
	 * 通过pattern模糊删除
	 * 
	 * @Title: deletePattern
	 * @param pattern
	 *
	 */
	public static void deletePattern(String pattern) {
		deletePattern(pattern, null);
	}

	/**
	 * 通过pattern模糊删除
	 * 
	 * @Title: deletePattern
	 * @param pattern
	 *
	 */
	public static void deletePattern(String pattern, Integer dbIndex) {
		try {
			Set<String> keys = keys(pattern, dbIndex);
			delete(keys, dbIndex);
		} catch (Exception e) {
			LOGGER.error("catch redis deletePattern error", e);
			throw e;
		}
	}

	/**
	 * 通过pattern查询key
	 * 
	 * @Title: deletePattern
	 * @param pattern
	 *
	 */
	public static Set<String> keys(String pattern) {
		try {
			return keys(pattern, null);
		} catch (Exception e) {
			LOGGER.error("catch redis deletePattern error", e);
			throw e;
		}
	}

	/**
	 * 通过pattern查询key
	 * 
	 * @Title: deletePattern
	 * @param pattern
	 * @param dbIndex
	 * @return
	 *
	 */
	public static Set<String> keys(String pattern, Integer dbIndex) {
		try {
			final RedisSerializer keySerializer = redisTemplate.getKeySerializer();
			Set<String> keys = redisTemplate.execute(new RedisCallback<Set<String>>() {
				@Override
				public Set<String> doInRedis(RedisConnection connection) throws DataAccessException {
					if (dbIndex != null) {
						connection.select(dbIndex);
					}
					return SerializationUtils.deserialize(connection.keys(keySerializer.serialize(pattern)), keySerializer);
				}
			});
			removeNullValue(keys);
			return keys;
		} catch (Exception e) {
			LOGGER.error("catch redis keys error", e);
			throw e;
		}
	}

	public static Boolean flushDB() {
		return flushDB(null);
	}

	public static Boolean flushDB(Integer dbIndex) {
		try {
			return redisTemplate.execute(new RedisCallback<Boolean>() {
				@Override
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
					if (dbIndex != null) {
						connection.select(dbIndex);
					}
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
		return DBSize(null);
	}

	public static Long DBSize(Integer dbIndex) {
		try {
			return redisTemplate.execute(new RedisCallback<Long>() {
				@Override
				public Long doInRedis(RedisConnection connection) throws DataAccessException {
					if (dbIndex != null) {
						connection.select(dbIndex);
					}
					return connection.dbSize();
				}
			}, true);
		} catch (Exception e) {
			LOGGER.error("catch redis DBSize error", e);
			throw e;
		}
	}

	private static byte[] serializeKey(Object key, final RedisSerializer keySerializer) {
		Assert.notNull(key, "non null key required");
		if (keySerializer == null && key instanceof byte[]) {
			return (byte[]) key;
		}
		return keySerializer.serialize(key);
	}

	private static byte[][] serializeKeys(Collection<String> keys, final RedisSerializer serializer) {
		if (CollectionUtils.isNotEmpty(keys)) {
			final byte[][] rawKeys = new byte[keys.size()][];
			int i = 0;
			for (String key : keys) {
				rawKeys[i++] = serializeKey(key, serializer);
			}
			return rawKeys;
		}
		return null;
	}

	private static void removeNullValue(Collection<?> list) {
		if (CollectionUtils.isNotEmpty(list)) {
			for (Iterator<?> it = list.iterator(); it.hasNext();) {
				if (it.next() == null)
					it.remove();
			}
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
