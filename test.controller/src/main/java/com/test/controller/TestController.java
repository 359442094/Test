package com.test.controller;

import com.test.common.annoation.ShowLogger;
import com.test.common.constant.ServiceConstant;
import com.test.common.util.AESUtil;
import com.test.common.util.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
@Api(tags = {"测试视图接口"})
@Controller
public class TestController {

    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation(value = "测试视图",notes = "测试视图")
    @ShowLogger(info = "测试视图")
    @RequestMapping(path = "/test",method = RequestMethod.GET)
    public String test(){
        if(!StringUtils.isEmpty(redisUtil.get(ServiceConstant.SERVICE_SESSION))){
            System.out.println("success");
            return "success";
        }else{
            System.out.println("index");
            return "index";
        }
    }
}
