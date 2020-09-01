package com.test.start.test.shouxian;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author chenjie
 * @date 2020-08-31
 */
@Data
public class ShouXianApiResult {

    private String result;

    private String msg;
    @JsonProperty(value = "policy_no")
    private String policyNo;

    private String company;

}
