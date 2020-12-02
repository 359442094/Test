package com.test.model.dto.lt;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author chenjie
 * @date 2020-11-18
 */
@Data
public class BaseRequest {

    //采购商编码
    @JSONField(name = "Purchaser")
    private String purchaser;
    //商户流水号
    @JSONField(name = "TranNo")
    private String tranNo;
    //充值帐号请求IP
    @JSONField(name = "Sign")
    private String sign;

}
