package com.test.start;

import com.test.common.annoation.RequestLimit;
import com.test.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author CJ
 * @date 2020/7/3
 */
@Aspect
@Component
@Slf4j
public class RequestLimitAop {

    private Map<String, Integer> map = new HashMap<String, Integer>();

    @Autowired
    private HttpServletRequest request;

    @Before("within(@org.springframework.stereotype.Controller *) && @annotation(limit)")
    public void requestLimit(JoinPoint joinPoint, RequestLimit limit) throws Exception {
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof HttpServletRequest) {
                request = (HttpServletRequest) args[i];
                break;
            }
        }
        if (request == null) {
            throw new ServiceException("方法中缺失HttpServletRequest参数");
        }
        String ip = request.getLocalAddr();
        String url = request.getRequestURL().toString();
        String key = "req_limit_".concat(url).concat(ip);
        if (map.get(key) == null || map.get(key) == 0) {
            map.put(key, 1);
        } else {
            map.put(key, map.get(key) + 1);
        }
        int count = map.get(key);
        if (count > 0) {
            /**
             * 使用工厂方法初始化一个ScheduledThreadPool
             */
            ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(2);
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    try {
                        map.remove(key);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            };
            newScheduledThreadPool.schedule(timerTask, limit.time(), TimeUnit.MILLISECONDS);
            //安排在指定延迟后执行指定的任务。task : 所要安排的任务。10000 : 执行任务前的延迟时间，单位是毫秒。
        }
        if (count > limit.count()) {
            String errorInfo = "用户IP[" + ip + "]访问地址[" + url + "]超过了限定的次数[" + limit.count() + "]";
            log.error(errorInfo);
            throw new ServiceException(errorInfo);
        }
    }

}
