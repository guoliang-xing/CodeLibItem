package com.xgl.study.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

/**
 *   略，具体看Jedis源码
 * @author Liang
 *
 */
public class JedisTest {
	
	private Jedis jedis;
	
	public JedisTest(String ip,int port) {
		jedis = new Jedis(ip,port);
		logger.info(jedis.ping("redis connect success !"));
	}
	
	private static Logger logger = LoggerFactory.getLogger(JedisTest.class);
	
	public static void main(String[] args) {
		JedisTest jt = new JedisTest("127.0.0.1",6379);
		jt.jedisStringTest();
	}
	
	public void jedisStringTest() {
		logger.info("set:{}",jedis.set("jedis","test",new SetParams().xx()));
		logger.info("get:{}",jedis.get("jedis"));
		logger.info("type:{}",jedis.type("jedis"));
		logger.info("exists:{}",jedis.exists("jedis"));
		logger.info("strlen:{}",jedis.strlen("jedis"));
	}

}