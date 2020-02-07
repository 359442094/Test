package com.test.common.util;

import com.test.common.constant.ErrorConstant;
import com.test.common.constant.ServiceConstant;
import com.test.common.dto.User;
import com.test.common.exception.ServiceException;
import com.test.model.domain.Test;
import com.test.model.domain.TestExample;
import com.test.model.domain.UserExample;
import com.test.model.persistence.TestMapper;
import com.test.model.persistence.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import javax.annotation.PostConstruct;
import java.util.List;

@Component
@Slf4j
public class Context {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private TestMapper testMapper;

    private static ThreadLocal<Test> userThreadLocal=new ThreadLocal<>();

    private static Context context;

    private static Test test;

    @PostConstruct
    public void init(){
        context=this;
    }

    public static void initUser(String sessionId){
        if(!StringUtils.isEmpty(sessionId)){
            String sId = AESUtil.decryptStart(sessionId);
            if(StringUtils.isEmpty(sId)){
                throw new ServiceException(ErrorConstant.ERROR_SESSION,"sessionId已失效");
            }
            if(sId.indexOf("&")<=-1){
                throw new ServiceException(ErrorConstant.ERROR_SESSION,"非法的sessionId");
            }
            String[] split = sId.split("&");
            String userName = split[0];
            String password = split[1];
            log.info("userName:"+userName);
            log.info("password:"+password);
            String str = String.valueOf(context.redisUtil.get(userName + password + sessionId));
            log.info("str:"+str);
            if(!StringUtils.isEmpty(str)){
                TestExample testExample=new TestExample();
                testExample.createCriteria()
                        .andNameEqualTo(userName)
                        .andPwdEqualTo(password);
                List<Test> tests = context.testMapper.selectByExample(testExample);
                System.out.println("tests:"+tests);
                if(tests.size()>0){
                    test = tests.get(0);
                }
            }
        }else{
            throw new ServiceException(ErrorConstant.ERROR_SESSION,"sessionId不能为空");
        }
    }

    public static void initThreadLocal(){
        if(test != null){
            userThreadLocal.set(test);
        }
    }

    public synchronized static Test getUser(String sessionId){
        initUser(sessionId);
        initThreadLocal();
        Test test = userThreadLocal.get();
        log.info("test:"+test);
        return test;
    }

}
