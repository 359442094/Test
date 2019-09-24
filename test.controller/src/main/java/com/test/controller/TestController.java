package com.test.controller;

import com.test.common.annoation.ShowLogger;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Api(tags = {"测试视图接口"})
@Controller
public class TestController {

    @ApiOperation(value = "测试视图",notes = "测试视图")
    @ShowLogger(info = "测试视图")
    @RequestMapping(path = "/test",method = RequestMethod.GET)
    public String test(){
        return "index";
    }

}
