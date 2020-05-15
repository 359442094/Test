package com.test.start.test.testWord;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.*;
import com.test.start.test.util.HttpClientUtil;
import com.test.start.test.util.SignatureUtils;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author CJ
 * @date 2020/5/14
 */
public class TestWord {

    /** 产品密钥ID，产品标识 */
    private final static String SECRETID = "a7b30c6bde9c81190d1923f7c1d889da";
    /** 产品私有密钥，服务端生成签名信息使用，请严格保管，避免泄露 */
    private final static String SECRETKEY = "48d9fe27eda56843143a47d9b50e64d1";
    /** 易盾反垃圾云服务文档检测在线提交地址 */
    private final static String checkFile_URL = "http://as-file.dun.163yun.com/v1/file/submit";
    private final static String CheckReuslt_URL = "http://as-file.dun.163yun.com/v1/file/query";


    /** 需检测的文档URL topdodo_1.jpg*/
    private final static String FILE_URL = "http://94.191.62.87:81/file/test.xlsx";

    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        //checkFile();
        //getCheckReusltByTaskId();
    }

    /**
     * 获取检查结果
     * */
    public static void getCheckReusltByTaskId() throws UnsupportedEncodingException {
        Map<String, String> params = new HashMap<>();
        // 1.设置公共参数
        params.put("secretId", SECRETID);
        params.put("version", "v1.1");
        params.put("timestamp", String.valueOf(System.currentTimeMillis()));
        params.put("nonce", String.valueOf(new Random().nextInt()));

        // 2.设置私有参数
        Set<String> taskIds = new HashSet<>();
        taskIds.add("5c847661c10b499189b088ea90f6f17f");
        params.put("taskIds", new Gson().toJson(taskIds));

        // 3.生成签名信息
        String signature = SignatureUtils.genSignature(SECRETKEY, params);
        params.put("signature", signature);

        // 4.发送HTTP请求，这里使用的是HttpClient工具包，产品可自行选择自己熟悉的工具包发送请求
        String response = HttpClientUtil.doPost(CheckReuslt_URL, params);

        System.out.println("response:"+response);
        CheckFileResponse checkFileResponse = JSONObject.parseObject(response, CheckFileResponse.class);

        String string = JSONObject.toJSONString(checkFileResponse);
        System.out.println("checkFileResponse:"+string);

    }

    /**
     * 开始检查
     * @throws UnsupportedEncodingException
     */
    public static void checkFile() throws UnsupportedEncodingException {
        //023b81b180b9435e8e02e87eaf098f8d
        Map<String, String> params = new HashMap<>();
        // 1.设置公共参数
        params.put("secretId", SECRETID);
        params.put("version", "v1.1");
        params.put("timestamp", String.valueOf(System.currentTimeMillis()));
        params.put("nonce", String.valueOf(new Random().nextInt()));

        // 2.设置私有参数
        //params.put("dataId", "ebfcad1c-dba1-490c-b4de-e784c2691768");
        params.put("dataId", "zc-utils-check-file");
        //检查url文件
        params.put("url", FILE_URL);
        /*params.put("content", "" +
                "操你妈" +
                "<img src=\"http://94.191.62.87:81/file/testSub.jpg\"/>" +
                "<img src=\"http://94.191.62.87:81/file/topdodo_1.jpg\"/>" +
                "<img src=\"http://94.191.62.87:81/file/yellow.png\"/>"
        );*/

        params.put("checkFlag", "7");
        params.put("ip", "123.115.77.137");
        params.put("account", "java@163.com");
        params.put("callback", "1234");
        //params.put("callback", "ebfcad1c-dba1-490c-b4de-e784c2691768");
        params.put("publishTime", String.valueOf(System.currentTimeMillis()));

        // 3.生成签名信息
        String signature = SignatureUtils.genSignature(SECRETKEY, params);
        params.put("signature", signature);

        // 4.发送HTTP请求，这里使用的是HttpClient工具包，产品可自行选择自己熟悉的工具包发送请求
        String response = HttpClientUtil.doPost(checkFile_URL,params);

        CheckResponse checkFileResponse = JSONObject.parseObject(response, CheckResponse.class);
        System.out.println("checkFileResponse:"+checkFileResponse);
    }

}
