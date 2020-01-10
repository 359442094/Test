package com.test.controller;

import com.test.common.annoation.ShowLogger;
import com.test.common.constant.ErrorConstant;
import com.test.common.constant.ServiceConstant;
import com.test.common.dto.Return;
import com.test.common.dto.UserLoginRequest;
import com.test.common.dto.UserLoginResponse;
import com.test.common.exception.ServiceException;
import com.test.common.util.AESUtil;
import com.test.common.util.Context;
import com.test.common.util.RedisUtil;
import com.test.model.domain.LoginTest;
import com.test.model.domain.Test;
import com.test.model.domain.Test1;
import com.test.model.domain.User;
import com.test.service.UserService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags = {"用户接口"})
@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtil redisUtil;

    @ShowLogger(info = "用户登录")
    @ApiOperation(value = "用户登录",notes = "用户登录")
    @RequestMapping(path = "/user/login",method = RequestMethod.POST)
    @ResponseBody
    public Return<UserLoginResponse> login(UserLoginRequest request){
        if(StringUtils.isEmpty(request)){
            throw new ServiceException(ErrorConstant.ERROR_PARAM,"登录参数为空");
        }else if(StringUtils.isEmpty(request.getUserName())){
            throw new ServiceException(ErrorConstant.ERROR_PARAM,"登录用户参数为空");
        }else if(StringUtils.isEmpty(request.getPassWord())){
            throw new ServiceException(ErrorConstant.ERROR_PARAM,"登录密码参数为空");
        }else{
            Test test = new Test();
            test.setName(request.getUserName());
            test.setPwd(request.getPassWord());
            Test test1 = userService.login(test);
            log.info("test1:"+test1);
            if(!StringUtils.isEmpty(test1)){
                String sessionId = AESUtil.encryptStart(request.getUserName()+"&"+request.getPassWord()+"&");
                redisUtil.set(request.getUserName()+request.getPassWord()+ServiceConstant.SERVICE_SESSION,sessionId);
                UserLoginResponse response=new UserLoginResponse();
                LoginTest loginTest=new LoginTest();
                loginTest.setSessionId(sessionId);
                loginTest.setUserId(test1.getId());
                loginTest.setUserName(test1.getName());
                loginTest.setPassWord(test1.getPwd());
                response.setUser(loginTest);
                return new Return<>(response);
            }
        }
        return new Return<>(null);
    }

    @ShowLogger(info = "获取上下文对象")
    @ApiOperation(value = "获取上下文对象",notes = "获取上下文对象")
    @RequestMapping(path = "/user/contextUser",method = RequestMethod.GET)
    @ResponseBody
    public Return<Test> getContextUser(@RequestParam String sessionId){
        return new Return<>(Context.getUser(sessionId));
    }

}
