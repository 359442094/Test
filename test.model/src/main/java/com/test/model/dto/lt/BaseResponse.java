package com.test.model.dto.lt;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author chenjie
 * @date 2020-11-18
 */
@Data
public class BaseResponse {

    @JSONField(name = "Result")
    private String result;
    @JSONField(name = "Msg")
    private String msg;
    @JSONField(name = "Data")
    private Object data;

}
