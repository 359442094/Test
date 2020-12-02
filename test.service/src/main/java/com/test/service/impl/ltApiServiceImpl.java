package com.test.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.test.common.util.HttpClientUtil;
import com.test.common.util.MD5Util;
import com.test.model.dto.lt.BaseRequest;
import com.test.model.dto.lt.BaseResponse;
import com.test.model.dto.lt.RechargeOrderSubmitRequest;
import com.test.model.dto.lt.RechargeOrderSubmitResponse;
import com.test.service.ltApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author chenjie
 * @date 2020-11-18
 */
@Slf4j
@Service
public class ltApiServiceImpl implements ltApiService {

    /**
     * 接口请求地址url前缀
     * */
    private static final String INTERFACE_URL_PREFIX = "http://apib.bm724.com";

    private static final String SECRET_KEY = "9B16D3E62F988D14A9A6EA35A58934A8";

    @Override
    public String getSign(Map<String, Object> requestMap) {
        requestMap.remove("sign");
        String[] objects = requestMap.keySet().toArray(new String[0]);
        Arrays.sort(objects);
        StringBuffer stringBuffer=new StringBuffer();
        Arrays.stream(objects).forEach(key->{
            stringBuffer.append(key+"="+requestMap.get(key)+"&");
        });
        stringBuffer.append("PurchaserMd5Key="+SECRET_KEY);
        String signBefore = stringBuffer.toString();
        log.info("话费充值接口 sign before:"+signBefore);
        String signAfter = MD5Util.getMd5(signBefore).toUpperCase();
        log.info("话费充值接口 sign after:"+signAfter);
        return signAfter;
    }

    @Override
    public BaseResponse orderSubmit(RechargeOrderSubmitRequest request) {
        Map<String,Object> requestMap = JSONObject.parseObject(JSONObject.toJSONString(request), Map.class);
        request.setSign(getSign(requestMap));
        String jsonString = JSONObject.toJSONString(request);
        log.info("话费充值接口入参"+jsonString);
        Map map = JSONObject.parseObject(jsonString, Map.class);
        String json = HttpClientUtil.doPostMap(INTERFACE_URL_PREFIX.concat("/NewApi/BillSubmit.ashx"),map);
        log.info("话费充值接口返回"+json);
        if(StringUtils.isEmpty(json)){
            return null;
        }
        return JSONObject.parseObject(json,BaseResponse.class);
    }

    public BaseResponse queryRechargeOrder(BaseRequest request){
        Map<String,Object> requestMap = JSONObject.parseObject(JSONObject.toJSONString(request), Map.class);
        request.setSign(getSign(requestMap));
        String jsonString = JSONObject.toJSONString(request);
        log.info("话费充值接口入参"+jsonString);
        Map map = JSONObject.parseObject(jsonString, Map.class);
        String json = HttpClientUtil.doPostMap(INTERFACE_URL_PREFIX.concat("/NewApi/BillQuery.ashx"),map);
        log.info("话费充值接口返回"+json);
        if(StringUtils.isEmpty(json)){
            return null;
        }
        return JSONObject.parseObject(json,BaseResponse.class);
    }

    public static void rechargeOrderSubmitTest(){
        ltApiService ltApiService=new ltApiServiceImpl();
        RechargeOrderSubmitRequest request=new RechargeOrderSubmitRequest();
        request.setPurchaser("111179");
        request.setNumber(1);
        request.setFaceValue(1);
        request.setTranNo("123456");
        request.setAccount("16621242385");
        request.setRequestIp("202.123.23.21");
        BaseResponse response = ltApiService.orderSubmit(request);
        System.out.println("response:"+response);
    }

    public static void queryRechargeOrderTest(){
        ltApiService ltApiService=new ltApiServiceImpl();
        BaseRequest request=new BaseRequest();
        request.setPurchaser("111179");
        request.setTranNo("123456");
        ltApiService.queryRechargeOrder(request);
    }

    public static void main(String[] args) {
        String url = "http://94.191.62.87:6002/rechargeOrderSubmitCallBack";
        Map<String,String> map=new HashMap<>();
        map.put("Status","SUCCESS");
        String post = HttpClientUtil.doPost(url, map);
        System.out.println(post);
        //rechargeOrderSubmitTest();
        //queryRechargeOrderTest();
    }

}
