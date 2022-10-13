package com.wxy.filter.dto;

/**
 * @Author:wxy
 * @Description:
 * @Date: 2022-10-13 16:17
 */
public class Result {
    /**
     * 返回码 200表示正常， 其余代表失败
     */
    private int code;

    /**
     * 错误信息
     */
    private String message;

    /**
     * 返回结果
     */
    private Object result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
