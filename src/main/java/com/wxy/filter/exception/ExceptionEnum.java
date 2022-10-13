package com.wxy.filter.exception;

/**
 * @Author:wxy
 * @Description:
 * @Date: 2022-10-13 16:15
 */
public enum ExceptionEnum {

    FREQUENT_REQUESTS(-1000, "请求频繁、请稍后重试！");

    private Integer code;
    private String msg;

    ExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
