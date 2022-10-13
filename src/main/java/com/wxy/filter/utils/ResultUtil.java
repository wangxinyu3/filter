package com.wxy.filter.utils;

import com.wxy.filter.dto.Result;

/**
 * @Author:wxy
 * @Description:
 * @Date: 2022-10-13 16:19
 */
public class ResultUtil {

    public static Result success(Object obj){
        Result result = new Result();
        result.setCode(200);
        result.setMessage("请求成功");
        result.setResult(obj);
        return result;
    }

    public static Result success(){
        return success(null);
    }

    public static Result error(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(msg);
        return result;
    }

    public static Result error(Exception e) {
        Result result = new Result();
        result.setCode(-1);
        result.setMessage("系统错误,请联系管理员!!!");
        return result;
    }
}
