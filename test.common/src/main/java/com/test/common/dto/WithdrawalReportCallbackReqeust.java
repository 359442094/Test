package com.test.common.dto;

import lombok.Data;


/**
 * @author chenjie
 * @date 2020-12-03
 */
@Data
public class WithdrawalReportCallbackReqeust {

    private String phone;

    private Integer status;

    private String reserve;

}
