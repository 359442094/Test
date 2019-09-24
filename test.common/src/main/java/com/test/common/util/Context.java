package com.test.common.util;

import com.test.common.constant.ErrorConstant;
import com.test.common.constant.ServiceConstant;
import com.test.common.dto.User;
import com.test.common.exception.ServiceException;
import com.test.model.domain.UserExample;
import com.test.model.persistence.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class Context {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserMapper userMapper;

    private static ThreadLocal<User> userThreadLocal=new ThreadLocal<>();

    private static Context context;

    private static User user;

    @PostConstruct
    public void init(){
        context=this;
    }

    public static void initUser(){
        if(!StringUtils.isEmpty(context.redisUtil.get(ServiceConstant.SERVICE_SESSION))){
            String sessionId = context.redisUtil.get(ServiceConstant.SERVICE_SESSION).toString();
            String sId = AESUtil.decryptStart(sessionId);
            if(sId.indexOf("&")<=-1){
                throw new ServiceException(ErrorConstant.ERROR_SESSION,"非法的sessionId");
            }
            String[] split = sId.split("&");
            String userName = split[0];
            String password = split[1];
            UserExample userExample=new UserExample();
            userExample.createCriteria()
                    .andNameEqualTo(userName)
                    .andPwdEqualTo(password);
            List<com.test.model.domain.User> users = context.userMapper.selectByExample(userExample);
            if(users.size()>0){
                user=ConvertUtil.convert(users.get(0));
            }
        }
    }

    public static void initThreadLocal(){
        if(user != null){
            userThreadLocal.set(user);
        }
    }

    public synchronized static User getUser(){
        initUser();
        initThreadLocal();
        return userThreadLocal.get();
    }

}
