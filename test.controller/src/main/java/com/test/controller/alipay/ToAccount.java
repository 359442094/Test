package com.test.controller.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundTransOrderQueryRequest;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.response.AlipayFundTransOrderQueryResponse;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;

import java.util.UUID;

/**
 * 文档地址:https://opendocs.alipay.com/apis/api_28/alipay.fund.trans.toaccount.transfer
 * alipay.fund.trans.toaccount.transfer(单笔转账到支付宝账户接口)
 * @author chenjie
 * @date 2020-09-23
 */
public class ToAccount {

    /**
     * 发起转账交易
     */
    public static void toAccount(String bizNo) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl,AlipayConfig.app_id,AlipayConfig.merchant_private_key,"json",AlipayConfig.charset,AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
        AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
        request.setBizContent("{" +
                "\"out_biz_no\":"+ bizNo +"," +
                "\"payee_type\":\"ALIPAY_LOGONID\"," +
                "\"payee_account\":\"xaflyj8540@sandbox.com\"," +
                "\"amount\":\"0.01\"," +
                "\"payer_show_name\":\"上海交通卡退款\"," +
                //"\"payee_real_name\":\"沙箱环境\"," +
                "\"remark\":\"转账备注\"" +
                "  }");
        AlipayFundTransToaccountTransferResponse response = alipayClient.execute(request);
        System.out.println("response.getMsg():"+response.getMsg());
        if(response.isSuccess()){
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
    }

    /**
     * 查询转账交易
     */
    public static void queryAccount(String bizNo) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl,AlipayConfig.app_id,AlipayConfig.merchant_private_key,"json",AlipayConfig.charset,AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
        AlipayFundTransOrderQueryRequest request = new AlipayFundTransOrderQueryRequest();
        request.setBizContent("{" +
                "\"out_biz_no\":"+bizNo+"," +
                "}");
        AlipayFundTransOrderQueryResponse alipayFundTransOrderQueryResponse = alipayClient.execute(request);
        System.out.println("response.getMsg():"+alipayFundTransOrderQueryResponse.getOrderId());
        if(alipayFundTransOrderQueryResponse.isSuccess()){
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
    }

    public static void main(String[] args) throws AlipayApiException {
        String bizNo = "bc59e909-cea6-40e2-9539-5d821a6528b8";//UUID.randomUUID().toString();
        System.out.println("bizNo:"+bizNo);
        //toAccount(bizNo);
        //queryAccount(bizNo);
    }
}
