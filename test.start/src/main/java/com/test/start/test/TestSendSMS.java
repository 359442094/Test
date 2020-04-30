package com.test.start.test;

import com.test.start.test.util.HttpClientUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author CJ
 * @date 2020/3/30
 */
public class TestSendSMS {
    public static void main(String[] args) {
        String url="http://192.168.0.182:8011/msg/audit?telNum="+"16621242385"+"&courseId="+"1234";
        /*Map<String,String> map=new HashMap<>();
        map.put("telNum","16621242385");
        map.put("courseId","1234");*/
        String json = HttpClientUtil.doPost(url, null);
        System.out.println("json:"+json);
    }
}
