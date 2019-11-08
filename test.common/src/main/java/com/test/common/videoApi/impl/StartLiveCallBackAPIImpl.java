package com.test.common.videoApi.impl;

import com.test.common.videoApi.StartLiveCallBackAPI;
import com.test.common.videoApi.StartLiveCallBackRequest;
import org.springframework.stereotype.Component;

/**
 * @author CJ
 * @date 2019/10/31
 */
@Component
public class StartLiveCallBackAPIImpl implements StartLiveCallBackAPI {
    @Override
    public StartLiveCallBackRequest process(StartLiveCallBackRequest request) {
        return request;
    }
}
