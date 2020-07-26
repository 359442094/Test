package com.test.controller;

import com.test.service.FlushIndexService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"璞康接口"})
@RestController
@Slf4j
public class PookQueryController {

    @Autowired
    private FlushIndexService flushIndexService;

    @ApiOperation(value = "璞康测试")
    @RequestMapping(path = "/test/pook",method = RequestMethod.GET)
    public Object test() throws Exception {
        String result = flushIndexService.flushLuceneIndex();
        return result;
    }

}
