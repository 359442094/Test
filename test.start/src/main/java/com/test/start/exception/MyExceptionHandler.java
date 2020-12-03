package com.test.start.exception;

import com.test.common.exception.Result;
import com.test.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenjie
 * @date 2020-12-03
 */
@Slf4j
@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result exceptionHandler(Exception e){
        log.error("1未知异常！原因是:"+e);
        return new Result("400",e.getMessage());
    }

    @ExceptionHandler(value = ServiceException.class)
    @ResponseBody
    public Result exceptionHandler(ServiceException e){
        log.error("2未知异常！原因是:"+e);
        return new Result("400",e.getMessage());
    }

}
