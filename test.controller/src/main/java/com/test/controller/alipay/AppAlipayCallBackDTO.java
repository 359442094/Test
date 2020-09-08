package com.test.controller.alipay;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName: AlipayCallBackDto
 * @Description: 支付宝回调入参
 * @author: huangsheng
 * @date: 2018/12/6 16:24
 * @Copyright: 2018 www.vcredit.com Inc. All rights reserved.
 */
public class AppAlipayCallBackDTO implements Serializable {
    private static long serialVersionUID = 6289630424887074947L;

    //商户订单号
    @JsonProperty(value = "out_trade_no")
    private String outTradeNo;

    //支付宝交易号
    @JsonProperty(value = "trade_no")
    private String tradeNo;

    //编码格式
    private String charset;

    //交易状态
    @JsonProperty("trade_status")
    private String tradeStatus;

    //订单标题
    @JsonProperty("subject")
    private String subject;

    //订单金额
    @JsonProperty("total_amount")
    private BigDecimal totalAmount;

    //实收金额
    @JsonProperty("receipt_amount")
    private BigDecimal receiptAmount;

    //开票金额
    @JsonProperty("invoice_amount")
    private BigDecimal invoiceAmount;

    //付款金额
    @JsonProperty("buyer_pay_amount")
    private BigDecimal buyerPayAmount;

    //通知时间
    @JsonProperty("notify_time")
    private Date notifyTime;

    //通知类型
    @JsonProperty("notify_type")
    private String notifyType;

    //通知校验ID
    @JsonProperty("notify_id")
    private String notifyId;

    //开发者的app_id
    @JsonProperty("app_id")
    private String appId;

    //接口版本
    private String version;

    //签名类型
    @JsonProperty("sign_type")
    private String signType;

    //签名
    private String sign;

    //商户业务号
    @JsonProperty("out_biz_no")
    private String outBizNo;

    //买家支付宝用户号
    @JsonProperty("buyerId")
    private String buyer_id;

    //买家支付宝用户号
    @JsonProperty("buyer_logon_id")
    private String buyerLogonId;

    //卖家支付宝用户号
    @JsonProperty("seller_id")
    private String sellerId;

    //卖家支付宝用户号
    @JsonProperty("seller_email")
    private String sellerEmail;

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getReceiptAmount() {
        return receiptAmount;
    }

    public void setReceiptAmount(BigDecimal receiptAmount) {
        this.receiptAmount = receiptAmount;
    }

    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public BigDecimal getBuyerPayAmount() {
        return buyerPayAmount;
    }

    public void setBuyerPayAmount(BigDecimal buyerPayAmount) {
        this.buyerPayAmount = buyerPayAmount;
    }

    public Date getNotifyTime() {
        return notifyTime;
    }

    public void setNotifyTime(Date notifyTime) {
        this.notifyTime = notifyTime;
    }

    public String getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(String notifyType) {
        this.notifyType = notifyType;
    }

    public String getNotifyId() {
        return notifyId;
    }

    public void setNotifyId(String notifyId) {
        this.notifyId = notifyId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getOutBizNo() {
        return outBizNo;
    }

    public void setOutBizNo(String outBizNo) {
        this.outBizNo = outBizNo;
    }

    public String getBuyer_id() {
        return buyer_id;
    }

    public void setBuyer_id(String buyer_id) {
        this.buyer_id = buyer_id;
    }

    public String getBuyerLogonId() {
        return buyerLogonId;
    }

    public void setBuyerLogonId(String buyerLogonId) {
        this.buyerLogonId = buyerLogonId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerEmail() {
        return sellerEmail;
    }

    public void setSellerEmail(String sellerEmail) {
        this.sellerEmail = sellerEmail;
    }
}
