package com.test.controller.alipay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * alipay.trade.wap.pay
 * 支付宝-沙箱手机app支付
 * @author chenjie
 * @date 2020-09-08
 */
@Slf4j
@Controller
@RequestMapping(path = "/alipay/app")
public class AppAlipayController {

    @RequestMapping(path = "/goAlipay",produces = "text/html; charset=UTF-8",method = RequestMethod.GET)
    @ResponseBody
    public String test(){
        String orderId=UUID.randomUUID().toString();
        return alipayTradeAppPay(orderId,"0.01");
    }
    /**
     * alipay.trade.app.pay：原生手机APP支付测试（外部商户APP唤起快捷SDK创建订单并支付）
     * @return
     */
    public String alipayTradeAppPay(String out_trade_no, String total_amount){
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
        AlipayTradeWapPayRequest alipayTradeWapPayRequest=new AlipayTradeWapPayRequest();
        //alipayTradeWapPayRequest.setReturnUrl("http://cj.ngrok2.xiaomiqiu.cn/alipay/app/asyncCallBack.action");
        alipayTradeWapPayRequest.setNotifyUrl("http://cj.ngrok2.xiaomiqiu.cn/alipay/app/asyncCallBack.action");
        JSONObject requestJson = new JSONObject();
        requestJson.put("out_trade_no", out_trade_no);
        requestJson.put("timeout_express", "10m");
        requestJson.put("total_amount", total_amount);
        requestJson.put("subject", "订单号:"+out_trade_no);
        //用户付款中途退出返回商户网站的地址
        requestJson.put("quit_url", "https://www.baidu.com/?tn=02003390_9_hao_pg");
        requestJson.put("product_code", "QUICK_WAP_PAY");
        String bizContent = requestJson.toJSONString();
        alipayTradeWapPayRequest.setBizContent(bizContent);
        String form = "";
        try {
            form = alipayClient.pageExecute(alipayTradeWapPayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
            log.error("支付宝错误信息{}", e.getErrMsg());
        }
        return form;
    }

    @RequestMapping(path = "/asyncCallBack",method = RequestMethod.POST)
    @ResponseBody
    public AppAlipayCallBackDTO appAlipay(HttpServletRequest request) throws AlipayApiException {
        Map<String, String> params = new HashMap<>();
        Set<Map.Entry<String, String[]>> entrySet = request.getParameterMap().entrySet();
        for (Map.Entry<String, String[]> entry : entrySet) {
            String name = entry.getKey();
            String[] values = entry.getValue();
            int valLen = values.length;
            if (valLen == 1) {
                params.put(name, values[0]);
            } else if (valLen > 1) {
                StringBuilder sb = new StringBuilder();
                for (String val : values) {
                    sb.append(",").append(val);
                }
                params.put(name, sb.toString().substring(1));
            } else {
                params.put(name, "");
            }
        }
        String paramsJson = JSON.toJSONString(params);
        AppAlipayCallBackDTO appAlipayCallBackDto = JSON.parseObject(paramsJson, AppAlipayCallBackDTO.class);
        log.info("支付宝回调参数{}", paramsJson);

        /*boolean signVerified1 = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset,AlipayConfig.sign_type);
        log.info("signVerified1:"+signVerified1);*/
        boolean signVerified2 = AlipaySignature.rsaCheckV2(params, AlipayConfig.alipay_public_key, AlipayConfig.charset,AlipayConfig.sign_type);
        log.info("signVerified1:"+signVerified2);
        return appAlipayCallBackDto;
    }

}
