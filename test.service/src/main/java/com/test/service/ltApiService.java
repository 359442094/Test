package com.test.service;

import com.test.model.dto.lt.BaseRequest;
import com.test.model.dto.lt.BaseResponse;
import com.test.model.dto.lt.RechargeOrderSubmitRequest;
import com.test.model.dto.lt.RechargeOrderSubmitResponse;

import java.util.Map;

/**
 * @author chenjie
 * @date 2020-11-18
 */
public interface ltApiService {

    String getSign(Map<String,Object> requestMap);

    //充值话费接口
    BaseResponse orderSubmit(RechargeOrderSubmitRequest requestDTO);
    //充值话费接口-异步回调
    //充值话费接口-实时查询
    BaseResponse queryRechargeOrder(BaseRequest request);
}
