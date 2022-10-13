package com.wxy.filter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:wxy
 * @Description:
 * @Date: 2022-10-13 14:35
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/test")
    public void test(){
        System.out.println("执行了test方法");
    }
}
