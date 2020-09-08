package com.test.start.test.vip;

import com.alibaba.fastjson.JSONObject;
import com.test.start.test.util.HttpClientUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenjie
 * @date 2020-09-02
 */
public class Test {

    public static void main(String[] args) {
        String url="https://www.zilvguoyuan.com/crawler-xslit-api/xslit/order/submit";
        Map<String,String> map=new HashMap<>();
        map.put("mobile","16621242385");
        String json = HttpClientUtil.doPostJson(url, JSONObject.toJSONString(map));
        System.out.println("json:"+json);
    }

}
