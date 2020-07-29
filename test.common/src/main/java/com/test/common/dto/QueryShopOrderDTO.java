package com.test.common.dto;

import lombok.Data;

import java.util.Date;

@Data
public class QueryShopOrderDTO {

    //订单主键编号
    private String orderId;
    //订单服务编号
    private String orderNum;
    //服务类型
    private String serviceType;
    //创建时间
    private Date createDate;
    //商品名称
    private String productName;
    //售后状态(-1 审核中,0审核通过,1驳回，2服务中，3已完成,4已关闭)
    private String produceType;

}
