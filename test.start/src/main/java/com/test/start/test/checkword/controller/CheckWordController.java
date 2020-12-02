package com.test.start.test.checkword.controller;

import com.test.common.videoApi.BaseResponse;
import com.test.start.test.checkword.dto.SensitiveWordFilterOneRequest;
import com.test.start.test.checkword.dto.SensitiveWordFilterRequest;
import com.test.start.test.checkword.dto.SensitiveWordFilterResponse;
import com.test.start.test.checkword.service.SensitiveWordService;
import com.test.start.test.checkword.service.TimedTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * 敏感词检查swagger地址
 * http://106.75.249.141:9999/swagger-ui.html
 * @author CJ
 * @date 2020/6/18
 */

/**
 * 开启定时任务
 * */
@EnableScheduling
@RestController
@Api(tags = {"内部检查文本API"}, value = "公共API-内部检查文本API")
@RequestMapping(path = "/util/my/core")
public class CheckWordController {

    @Autowired(required = false)
    private SensitiveWordService sensitiveWordService;
    @Autowired
    private TimedTaskService timedTaskService;

    @ApiOperation(value = "内部检查多个文本接口", notes = "内部检查多个文本接口")
    @RequestMapping(path = "/checkWordList", method = RequestMethod.POST)
    public SensitiveWordFilterResponse checkWord(SensitiveWordFilterRequest request) throws ParseException {
        return sensitiveWordService.filter(request);
    }

    @ApiOperation(value = "内部检查单个文本接口", notes = "内部检查单个文本接口")
    @RequestMapping(path = "/checkWordOne", method = RequestMethod.POST)
    public SensitiveWordFilterResponse checkOneWord(SensitiveWordFilterOneRequest oneRequest) throws ParseException {
        SensitiveWordFilterRequest request=new SensitiveWordFilterRequest();
        request.setWords(Arrays.asList(oneRequest.getWord()));
        return sensitiveWordService.filter(request);
    }

    /**
     * 更新敏感词全文索引
     * 每天晚上12点/次
     * @return
     * @throws NoSuchAlgorithmException
     */
    /*@RequestMapping(path = "/flushLuceneIndex", method = RequestMethod.GET)
    @ApiOperation(value = "更新敏感词全文索引", notes = "更新敏感词全文索引")
    @Scheduled(cron = "0 0 0 * * ?")
    @ResponseBody
    public BaseResponse flushLuceneIndex() throws Exception {
        String s = timedTaskService.flushLuceneIndex();
        BaseResponse response = new BaseResponse();
        response.success();
        return response;
    }*/

}
