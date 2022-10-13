package com.wxy.filter.redis.service.impl;

import com.wxy.filter.redis.service.IRedisService;
import com.wxy.filter.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.util.Set;

/**
 * @Author:wxy
 * @Description:
 * @Date: 2022-10-13 14:54
 */
@Slf4j
@Service
public class RedisServiceImpl implements IRedisService {

    private String buildKey(String key){
        return Constant.REDIS_PPOJECT_PREX + key;
    }

    private JedisPool jedisPool;

    public RedisServiceImpl(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    private Jedis getResource() {
        return jedisPool.getResource();
    }

    @Override
    public Integer zCount(String key, double start, double end) {
        Jedis jedis = null;
        Integer result = null;
        try {
            key = buildKey(key);
            jedis = getResource();
            Set<String> set = jedis.zrangeByScore(key, start, end);
            result = set.size();
        } catch (Exception e) {
            log.error("=====ZSet RangeByScore Error : " + e.getMessage(), e);
        }finally{
            returnResource(jedis);
        }
        return result;
    }

    @Override
    public Pipeline getPipeline() {
        Pipeline p = null;
        Jedis jedis = null;
        try{
            jedis = getResource();
            p = jedis.pipelined();
        }catch (Exception e){
            log.error("========获取管道对象失败==========");
        }finally {
            returnResource(jedis);
        }
        return p;
    }

    public void returnResource(Jedis jedis) {
        if(jedis != null){
            jedis.close();
        }
    }
}
