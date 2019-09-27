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
import com.test.model.domain.User;
import com.test.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"用户接口"})
@RestController
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
        }else if(StringUtils.isEmpty(request.getUsername())){
            throw new ServiceException(ErrorConstant.ERROR_PARAM,"登录用户参数为空");
        }else if(StringUtils.isEmpty(request.getPassword())){
            throw new ServiceException(ErrorConstant.ERROR_PARAM,"登录密码参数为空");
        }else{
            User user1 = new User();
            user1.setName(request.getUsername());
            user1.setPwd(request.getPassword());
            com.test.common.dto.User user = userService.login(user1);
            if(!StringUtils.isEmpty(user)){
                String sessionId = AESUtil.encryptStart(request.getUsername()+"&"+request.getPassword()+"&");
                redisUtil.set(ServiceConstant.SERVICE_SESSION,sessionId);
                UserLoginResponse response=new UserLoginResponse();
                response.setUser(user);
                return new Return<>(response);
            }
        }
        return new Return<>(null);
    }

    @ShowLogger(info = "获取上下文对象")
    @ApiOperation(value = "获取上下文对象",notes = "获取上下文对象")
    @RequestMapping(path = "/user/contextUser",method = RequestMethod.GET)
    @ResponseBody
    public Return<com.test.common.dto.User> getContextUser(){
        return new Return<>(Context.getUser());
    }

}
