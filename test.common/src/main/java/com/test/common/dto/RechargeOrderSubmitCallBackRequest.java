package com.test.common.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author chenjie
 * @date 2020-11-18
 */
@Data
public class RechargeOrderSubmitCallBackRequest {

    //充值账号
    @JSONField(name = "Account")
    private String account;
    //商户余额(不可作为商户实时余额对待，仅供参考)
    @JSONField(name = "Balance")
    private String balance;
    //平台订单号
    @JSONField(name = "OrderCode")
    private String orderCode;
    //订单金额
    @JSONField(name = "Price")
    private String price;
    //备注说明
    @JSONField(name = "Remark")
    private String remark;
    //充值订单状态(SUCCESS 或 ERROR)
    @JSONField(name = "Status")
    private String status;
    //商户流水号
    @JSONField(name = "TranNo")
    private String tranNo;
    //充值帐号请求IP
    @JSONField(name = "Sign")
    private String sign;

}
