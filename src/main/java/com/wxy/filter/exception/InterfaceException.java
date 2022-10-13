package com.wxy.filter.exception;

/**
 * @Author:wxy
 * @Description:
 * @Date: 2022-10-13 16:14
 */
public class InterfaceException extends RuntimeException{

    private Integer code;

    public InterfaceException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMsg());
        this.code = exceptionEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }
}
