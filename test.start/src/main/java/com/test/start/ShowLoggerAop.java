package com.test.start;

import com.test.common.annoation.ShowLogger;
import com.test.common.util.TimesTaskUtil;
import lombok.extern.log4j.Log4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Component
@Log4j
public class ShowLoggerAop {

    @Autowired
    private HttpServletRequest request;

    private static long start = 0;

    private static long end = 0;

    @Pointcut(value = "@annotation(com.test.common.annoation.ShowLogger)")
    public void pointcut() {
    }

    @Before(value = "pointcut()")
    public void before(JoinPoint point) {
        start = System.currentTimeMillis();
        log.info("----------------  start  ----------------");

        showLoggerInfo(point);

        Object[] args = point.getArgs();
        for (Object arg : args) {
            log.info("request:[" + arg.toString() + "]");
        }
    }

    public void showLoggerInfo(JoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        if (method.isAnnotationPresent(ShowLogger.class)) {
            ShowLogger showLogger = method.getAnnotation(ShowLogger.class);
            log.info("url:[" + request.getRequestURI() + "]");
            log.info("info:[" + showLogger.info() + "]");
            log.info("method:[" + method.getName() + "]");
        }
    }

    @AfterReturning(value = "pointcut()", returning = "result")
    public void afterReturn(Object result) {
        if (result != null) {
            log.info("response:[" + result.toString() + "]");
        } else {
            log.info("response:[Null]");
        }
        end = System.currentTimeMillis();
        log.info("start:"+start);
        log.info("end:"+end);
        String processTime = TimesTaskUtil.process(start, end);
        log.info("请求-响应统计用时：" + processTime);
        log.info("----------------  end  ----------------");
    }

}
