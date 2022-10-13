package com.wxy.filter.redis.service;


import redis.clients.jedis.Pipeline;

/**
 * @Author:www
 * @Description:
 * @Date: 2018-3-19 10:59
 */
public interface IRedisService {

    /**
     * @date:2022-10-13 14:52
     * @author:wxy
     * @description: 根据score范围查询ZSet中元素总数
     * @param key   主键
     * @param start score范围开始
     * @param end   score范围结束
     */
    Integer zCount(String key, double start, double end);

    /**
     * @date:2022-10-13 14:52
     * @author:wxy
     * @description: 获取管道对象
     * @param
     */
    Pipeline getPipeline();
}
