package com.test.controller.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * alipay.trade.page.pay(统一收单下单并支付页面接口)
 * 电脑网站支付
 * 支付宝-沙箱扫码支付
 */
@Slf4j
@Controller
@RequestMapping("/alipay/qrcode")
public class QrCodeAlipayController {

    /* *
     * 前往支付宝第三方网
     * @return
     * @throws Exception*/
    @RequestMapping(value = "/goAlipay", produces = "text/html; charset=UTF-8",method = RequestMethod.GET)
    @ResponseBody
    public String goAlipay() throws Exception {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
        //支付成功或者关闭页面之后跳转的url
        alipayRequest.setReturnUrl("https://www.baidu.com/?tn=02003390_9_hao_pg");
        String out_trade_no =UUID.randomUUID().toString();
        //付款金额，必填
        String total_amount = "10";
        //订单名称，必填
        String subject = "测试订单";
        //商品描述，可空
        String body = null;
        // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
        String timeout_express = "10m";
        if(null!=total_amount){ //支付金额不等于空
            alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                    + "\"total_amount\":\""+ total_amount +"\","
                    + "\"subject\":\""+ subject +"\","
                    + "\"body\":\""+ body +"\","
                    + "\"timeout_express\":\""+ timeout_express +"\","
                    + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
            //请求
            AlipayTradePagePayResponse alipayTradePagePayResponse = alipayClient.pageExecute(alipayRequest);
            String result = alipayTradePagePayResponse.getBody();
            log.info("result:"+result);
            return result;
        }
        return "订单信息错误!";
    }

    /**
     * 支付宝同步通知页面,成功返回首页
     * 当用户第一次取消支付之后，同步接口没有响应(用异步可以解决)
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(path = "/synchronizationCallBack",method = RequestMethod.POST)
    @ResponseBody
    public String alipayReturnNotice(Model model,HttpServletRequest request, HttpServletRequest response) throws Exception {
        log.info("支付成功, 进入同步通知接口...");
        //获取支付宝GET过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        log.info("sign:"+params.get("sign"));

        boolean signVerified1 = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, "UTF-8", AlipayConfig.sign_type);

        log.info("signVerified1:"+signVerified1);

        boolean signVerified2 = AlipaySignature.rsaCheckV2(params, AlipayConfig.alipay_public_key, "UTF-8", AlipayConfig.sign_type);

        log.info("signVerified2:"+signVerified2);

        //——请在这里编写您的程序（以下代码仅作参考）——
        //if(signVerified) {
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
            log.info("********************** 支付成功(支付宝同步通知) **********************");
            log.info("* 订单号: {}", out_trade_no);
            log.info("* 支付宝交易号: {}", trade_no);
            log.info("* 实付金额: {}", total_amount);
            log.info("***************************************************************");
        /*}else {
            log.info("支付, 验签失败...");
        }*/
        return "成功返回首页";
    }

    /**
     * 支付宝异步 通知页面
     * 推荐使用异步通知
     * (异步支付页面需要自行做逻辑处理 例如:支付页面加一个按钮，点击请求查询状态的接口，支付中不能关闭，支付成功或者失败的可以关闭)
     * @param request
     * @param response
     * @return
     * @throws Exception
*/
    @RequestMapping(path = "/asyncCallBack",method = RequestMethod.POST)
    public String alipayNotifyNotice(HttpServletRequest request, HttpServletRequest response) throws Exception {
        log.info("支付成功, 进入异步通知接口...");
        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        log.info("sign:"+params.get("sign"));

        /*boolean signVerified1 = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, "UTF-8", AlipayConfig.sign_type);

        log.info("signVerified1:"+signVerified1);*/

        boolean signVerified2 = AlipaySignature.rsaCheckV2(params, AlipayConfig.alipay_public_key, "UTF-8", AlipayConfig.sign_type);

        log.info("signVerified2:"+signVerified2);

        //——请在这里编写您的程序（以下代码仅作参考）——
         /*实际验证过程建议商户务必添加以下校验：
        1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
        2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
        3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
        4、验证app_id是否为该商户本身。*/

        //if(signVerified) {//验证成功
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

            //付款金额 到这里获取到这些信息就可以了，下面的不用看
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");

            if(trade_status.equals("TRADE_FINISHED")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意： 尚自习的订单没有退款功能, 这个条件判断是进不来的, 所以此处不必写代码
                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
            }else if (trade_status.equals("TRADE_SUCCESS")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //付款完成后，支付宝系统发送该交易状态通知

                log.info("********************** 支付成功(支付宝异步通知) **********************");
                log.info("* 订单号: {}", out_trade_no);
                log.info("* 支付宝交易号: {}", trade_no);
                log.info("* 实付金额: {}", total_amount);
                log.info("***************************************************************");
            }
            log.info("支付成功...");
        /*}else {//验证失败
            log.info("支付, 验签失败...");
        }*/
        return "redirect:https://www.baidu.com/?tn=02003390_9_hao_pg";
    }

}