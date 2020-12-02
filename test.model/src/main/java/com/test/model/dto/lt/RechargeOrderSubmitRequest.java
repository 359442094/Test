package com.test.model.dto.lt;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Purchaser	是	string	采购商编码	110123
 * Number	是	int	采购数量	固定1
 * FaceValue	是	int	采购面值	充值面值
 * TranNo	是	string	商户流水号	不可重复，唯一，小于50位
 * Account	是	string	充值帐号	手机号
 * RequestIp	是	string	充值帐号请求IP	123.123.123.123
 * CallBackUrl	否	string	回调地址（可不传，后台绑定）	有效外网地址
 * Sign	是	string	签名（MD5,32位大写）,加密规则见头部说明	CF86901893588B769CCEFBF5444DE034
 *
 * @author chenjie
 * @date 2020-11-18
 */
@Data
public class RechargeOrderSubmitRequest extends BaseRequest{

    //采购商编码
    //@JsonProperty(value = "Purchaser")
    /*@JSONField(name = "Purchaser")
    private String purchaser;*/
    //采购数量(固定1)
    @JSONField(name = "Number")
    private Integer number;
    //采购面值
    @JSONField(name = "FaceValue")
    private Integer faceValue;
    //商户流水号
    /*@JSONField(name = "TranNo")
    private String tranNo;*/
    //充值账号
    @JSONField(name = "Account")
    private String account;
    //充值帐号请求IP
    @JSONField(name = "RequestIp")
    private String requestIp;
    //充值帐号请求IP
    @JSONField(name = "CallBackUrl")
    private String callBackUrl;
    //充值帐号请求IP
    /*@JSONField(name = "Sign")
    private String sign;*/

}
