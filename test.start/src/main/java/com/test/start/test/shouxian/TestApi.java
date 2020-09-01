package com.test.start.test.shouxian;

import com.alibaba.fastjson.JSONObject;
import com.test.start.test.util.HttpClientUtil;
import com.test.start.test.util.MD5Util;
import org.apache.commons.lang.CharSet;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * 寿险获客
 * @author chenjie
 * @date 2020-08-31
 */
public class TestApi {

    private static final String secretKey = "k5OzTm4Q";
    private static final String pipeCode = "XKD-1001";
    private static final String receiveMedia = "金融类";

    public static String test() throws UnsupportedEncodingException {
        String url="http://api.yuxianloo.com/insurance/deliver";
        Map<String,String> map=new HashMap<>();
        map.put("pipeCode",pipeCode);
        map.put("name","陈杰");
        map.put("mobile","16621242386");
        //map.put("birthday","1997-03-06");
        //传身份证可不传出生日期和性别
        map.put("idcard","430426199703061392");
        //map.put("gender","M");
        //map.put("city","上海");
        map.put("ipAddress","127.0.0.1");
        map.put("userAgent","Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 " +
                "like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko)" +
                " Version/11.0 Mobile/15A372 Safari/604.1");
        //map.put("verificationCode","123456");
        map.put("receiveMedia",receiveMedia);
        map.put("applyTime","2020-08-31 11:03:32");
        //map.put("lables","");
        //map.put("survey","");
        String sign = MD5Util.getMD5(map.get("mobile")+map.get("pipeCode")+secretKey).toLowerCase();
        System.out.println("sign:"+sign);
        map.put("encryptSign", sign);

        new String("".getBytes(),"UTF-8");

        //String json = "{\"encryptSign\":\"fcdeb2403ebe914fbe4c599af31c9576\",\"idCard\":\"430426199703061392\",\"name\":\"陈杰\",\"mobile\":\"16621242386\",\"ipAddress\":\"127.0.0.1\",\"userAgent\":\"Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1\",\"pipeCode\":\"TEST-1001\",\"receiveMedia\":\"金融类\",\"applyTime\":\"2020-08-31 11:03:32\",\"verificationCode\":\"123456\"}";
        String json = JSONObject.toJSONString(map);
        String doPost = HttpClientUtil.doPostJson(url,json);
        System.out.println("json:"+json);
        return doPost;
    }

    public static boolean testResult(String resultJson){
        ShouXianApiResult apiResult = JSONObject.parseObject(resultJson, ShouXianApiResult.class);
        if(!StringUtils.isEmpty(apiResult)&&"SUCCEEDED".equals(apiResult.getResult())){
            return true;
        }
        System.out.println("apiResult:"+apiResult);
        return false;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        //测试环境接口
        String test = test();
        //String resultJson = "{\"result\":\"SUCCEEDED\",\"msg\":\"领取成功\",\"policy_no\":null,\"company\":\"平安保险\"}";
        boolean testResult = testResult(test);
        System.out.println("testResult:"+testResult);
    }

}
