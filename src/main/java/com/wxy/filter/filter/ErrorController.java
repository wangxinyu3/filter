package com.wxy.filter.filter;

import com.wxy.filter.exception.ExceptionEnum;
import com.wxy.filter.exception.InterfaceException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:wxy
 * @Description:
 * @Date: 2022-10-13 16:13
 */
@RestController
@RequestMapping("/filter")
public class ErrorController {
    @RequestMapping("/frequent")
    public void frequent() {
        throw new InterfaceException(ExceptionEnum.FREQUENT_REQUESTS);
    }
}
