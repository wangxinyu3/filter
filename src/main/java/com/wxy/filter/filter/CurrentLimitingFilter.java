package com.wxy.filter.filter;

import com.wxy.filter.dto.Properties;
import com.wxy.filter.redis.service.IRedisService;
import com.wxy.filter.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import redis.clients.jedis.Pipeline;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * @Author:wxy
 * @Description: 基于Redis的ZSet实现限流
 * 使用@WebFilter的情况下不应该使用 @Component。
 * @Component 会通知Spring扫描此Class, 由于没有urlPatterns, 会生成一个 [/*]的filter，导致所有请求url都被拦截
 * @WebFilter 会生成一个 [/jsj/*]的 filter    @WebFilter 需要在启动类Application 添加 @ServletComponentScan
 *
 * 使用Filter两种方式：  1. @WebFilter + 启动类Application 添加 @ServletComponentScan
 *                    2. @Component + 手动注入bean
 *           ********  @WebFilter 和 @Component 不可同时使用 **********
 * @Date: 2022-10-13 14:39
 */
@WebFilter( urlPatterns = { "/api/*" }, filterName = "currentLimitingFilter")
@Order(2)
@Slf4j
public class CurrentLimitingFilter implements Filter {

    private static final Long TIME_INTERVAL = 60 * 1000L;   //时间间隔 默认分钟

    @Autowired
    private Properties properties;

    @Autowired
    private IRedisService redisService;


    @Override
    public void init(FilterConfig filterConfig){

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("=============进入了 CurrentLimitingFilter ==============");

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //校验接口请求次数
        Integer maxCount = properties.getMaxCount();    //最大访问次数
        Long time = properties.getTime() * TIME_INTERVAL;   //单位时间

        String servletPath = request.getServletPath();   //获取请求路由，不包括项目名
        String[] methodPaths = servletPath.split("/");
        String methodPath = methodPaths[methodPaths.length - 1];
        String limitKey = properties.getRedisKeyPrefix() + methodPath;
        long nowTime = System.currentTimeMillis();
        Integer zCount = redisService.zCount(limitKey, nowTime - time, nowTime);
        if (zCount != null && zCount >= maxCount){
            request.getRequestDispatcher("/filter/frequent").forward(request, response);
        }else {
            Pipeline p = redisService.getPipeline();
            p.zadd(Constant.REDIS_PPOJECT_PREX + limitKey, nowTime, UUID.randomUUID().toString());
            //记录接口调用次数
            p.incr(Constant.REDIS_PPOJECT_PREX + properties.getRedisKeyPrefix() + "count"); //该Controller下所有接口访问次数
            p.incr(Constant.REDIS_PPOJECT_PREX +  limitKey + "_count");     //单个接口访问次数
            p.sync();
            filterChain.doFilter(request,response);
        }
    }

    @Override
    public void destroy() {

    }

}
