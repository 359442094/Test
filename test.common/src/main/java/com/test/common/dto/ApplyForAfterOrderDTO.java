package com.test.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 申请售后订单
 */
@Data
public class ApplyForAfterOrderDTO {

    //商品名称
    private int productId;
    //商品型号
    //private String productType;
    //服务类型(0,安装，1维修，2配件更换)
    private String serviceType;
    //服务开始时间
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date serviceStartDate;
    //服务结束时间
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date serviceEndDate;
    //联系人姓名
    private String name;
    //性别(0:男 1:女)
    private String sex;
    //手机号码
    private String phone;
    //省
    private String province;
    //市
    private String city;
    //区
    private String district;
    //详细地址
    private String address;
    //描述
    private String shopDesc;

}
