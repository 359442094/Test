package com.test.common.videoApi.impl;

import com.alibaba.fastjson.JSONObject;
import com.test.common.exception.ServiceException;
import com.test.common.util.APIConstant;
import com.test.common.util.ExceptionConstant;
import com.test.common.util.HttpClientUtil;
import com.test.common.util.MD5HexUtil;
import com.test.common.videoApi.BaseCheck;
import com.test.common.videoApi.SettingLiveCallBackAPI;
import com.test.common.videoApi.SettingLiveCallBackRequest;
import com.test.common.videoApi.SettingLiveCallBackResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author CJ
 * @date 2019/10/31
 */
@Slf4j
@Component
public class SettingLiveCallBackAPIImpl extends BaseCheck implements SettingLiveCallBackAPI {
    @Override
    public SettingLiveCallBackResponse process(SettingLiveCallBackRequest request) {
        Map<String,String> map=new HashMap<>();
        map.put("appId", APIConstant.API_APP_ID);
        map.put("timestamp",APIConstant.API_TIMESTAMP_VALUE);
        if(!StringUtils.isEmpty(request.getUrl())){
            map.put("url",request.getUrl());
        }
        map.put("sign", MD5HexUtil.Ksort(map));
        String url="http://api.polyv.net/live/v2/user/"+APIConstant.API_USER_ID+"/set-record-callback";
        String json = HttpClientUtil.doPost(url, map);
        log.info("设置录制回调通知url结果:"+json);
        return processJson(json);
    }

    @Override
    public SettingLiveCallBackResponse processJson(String json) {
        checkJson(json);
        JSONObject object = JSONObject.parseObject(json);
        Object code = object.get("code");
        if(!org.springframework.util.StringUtils.isEmpty(code) && "200".equals(code.toString())){
            return new SettingLiveCallBackResponse(true);
        }else{
            throw new ServiceException(ExceptionConstant.EXCEPTION_JSON_ERROR," json:"+json);
        }
    }
}
