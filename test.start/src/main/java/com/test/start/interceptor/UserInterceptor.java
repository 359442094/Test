package com.test.start.interceptor;

import com.test.common.constant.ErrorConstant;
import com.test.common.constant.ServiceConstant;
import com.test.common.exception.ServiceException;
import com.test.common.util.AESUtil;
import com.test.common.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class UserInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String userName = httpServletRequest.getParameter("userName");
        String passWord = httpServletRequest.getParameter("passWord");
        log.info("userName:"+userName);
        log.info("passWord:"+passWord);
        String sessionId = String.valueOf(redisUtil.get(userName+passWord+ServiceConstant.SERVICE_SESSION));
        if(StringUtils.isEmpty(sessionId)){
            throw new ServiceException(ErrorConstant.ERROR_LOGIN_TIMEOUT,"请重新登录");
        }else{
            String decryptSession = AESUtil.decryptStart(sessionId);
            log.info("decryptSession:"+decryptSession);
            log.info("sessionId:"+sessionId);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

}
