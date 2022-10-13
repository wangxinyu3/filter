package com.wxy.filter.exception;

import com.wxy.filter.dto.Result;
import com.wxy.filter.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author:wxy
 * @Description:
 * @Date: 2022-10-13 16:17
 */
@RestControllerAdvice
public class ExceptionHandle {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandler(Exception e){
        if (e instanceof InterfaceException){
            InterfaceException myException = (InterfaceException) e;
            logger.error(e.getMessage(), e);
            return ResultUtil.error(myException.getCode(), myException.getMessage());
        }
        logger.error("【异常信息】:", e);
        return ResultUtil.error(e);
    }
}
