package com.wxy.filter.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author:wxy
 * @Description:
 * @Date: 2022-10-13 15:12
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {
    Logger logger = LoggerFactory.getLogger(RedisCacheConfiguration.class);

    @Value("${spring.redis.host-name}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.pool.max-wait}")
    private long maxWaitMillis;

    @Value("${spring.redis.pool.maxTotal}")
    private int maxTotal;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.database}")
    private Integer database;

    @Bean
    public JedisPool redisPoolFactory() {
        logger.info("JedisPool注入成功！！");
        logger.info("redis地址：" + host + ":" + port);
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //redis连接池最大的idle(空闲状态)连接个数
        jedisPoolConfig.setMaxIdle(maxIdle);
        //当borrow一个redis示例的时候，超过maxWait时间，就会报JedisConnectionException
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        //如果已经分配了maxActive个数的redis实例，如果再去获取，就会出现exhausted(耗尽状态)
        jedisPoolConfig.setMaxTotal(maxTotal);
        //表示连接池在创建链接的时候会先测试一下链接是否可用，这样可以保证连接池中的链接都可用的
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setTestOnReturn(true);
        if ("".equals(password)) password = null;
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password, database);
        return jedisPool;
    }
}
