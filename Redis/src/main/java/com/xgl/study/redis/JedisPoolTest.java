package com.xgl.study.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 *   略，具体看Jedis源码
 * @author Liang
 *
 */
public class JedisPoolTest {
	
	private JedisPool jedisPool;
	
	public JedisPoolTest(String ip,int port) {
		
		GenericObjectPoolConfig<Jedis> poolConfig = new GenericObjectPoolConfig<>();
		poolConfig.setMaxTotal(10);
		jedisPool = new JedisPool(poolConfig,"127.0.0.1",6379);
		jedisPool.getResource().set("jedisPool","testPool");
	}
}