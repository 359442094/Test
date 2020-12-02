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

    /**
     * String url="https://www.zilvguoyuan.com/crawler-xslit-api/xslit/order/submit";
     *         Map<String,String> map=new HashMap<>();
     *         map.put("mobile","16621242385");
     *         String json = HttpClientUtil.doPostJson(url, JSONObject.toJSONString(map));
     *         System.out.println("json:"+json);
     * */

    public void submitOrder(){
        String url="http://www.xslit.com/api.php?act=pay";
        Map<String,String> map=new HashMap<>();
        map.put("tid","1241");
        map.put("user","荟分数科");
        map.put("pass","hf2019");
        map.put("num","1");
        map.put("input1","16621242385");
        String json = HttpClientUtil.doPost(url,map);
        //System.out.println("json:"+json);
        //{"code":-2,"message":"余额不足，购买此商品还差9.8元"}
        //{"code":-1,"message":"用户名或密码不正确"}
        System.out.println("json:"+json);
        JSONObject jsonObject = JSONObject.parseObject(json);
        System.out.println("jsonObject:"+jsonObject.getString("message"));
    }

    public static void main(String[] args) {
        //String url="http://www.xslit.com/api.php?act=pay&tid=1241&user=荟分数科&pass=hf2019";
        String url="http://www.xslit.com/api.php?act=search&id=11640";
        String json = HttpClientUtil.doPost(url);
        //System.out.println("json:"+json);
        //{"code":-2,"message":"余额不足，购买此商品还差9.8元"}
        //{"code":-1,"message":"用户名或密码不正确"}
        System.out.println("json:"+json);
        JSONObject jsonObject = JSONObject.parseObject(json);
        System.out.println("jsonObject:"+jsonObject.getString("message"));
    }

}
