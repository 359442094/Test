package com.test.start.test.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 广州联通充值查询接口返回参数
 * @author chenjie
 * @date 2020-09-17
 */
@Data
public class LianTongQueryOrderDto {

    //用户编号
    private String userId;
    //订购的手机号码
    private String serialNumber;
    //入网日期(激活日期) yyyyMM-dd HH:mm:ss
    private String inNetDate;
    //订单变更时间yyyyMM
    private String inNetMonth;
    //当前号码状态
    private String state;
    //产品名称
    private String productName;
    //首次充值金额
    private int firstCMoney;
    //首次充值时间yyyyMM-dd HH:mm:ss
    private String firstCTime;
    //是否有充值标签 (是/否)
    //@JsonFormat(pattern = "c_flag")
    private String cFlag;
    //CBSS系统的发展人编码
    //@JsonFormat(pattern = "cbss_developer_id")
    private String cbssDeveloperId;

}
