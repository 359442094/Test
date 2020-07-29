package com.test.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 添加或者修改售后订单信息
 */
@Data
public class SaveOrEditAfterOrderDTO {

    //售后服务编号(添加的时候可以不传，修改的时候必须传)
    private int afterOrderId;
    //联系人
    private String name;
    //性别(0:男 1:女)
    private String sex;
    //手机号码
    private String phone;
    //门店名称
    private String shopName;
    //省
    private String province;
    //市
    private String city;
    //区
    private String district;
    //详细地址
    private String address;
    //服务类型
    private String serviceType;
    //预约日期
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd" , timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    //时间段
    private String timeQuantum;

    //服务人员编号
    private int serviceUserId;
    //服务人手机号码
    private String serviceUserPhone;
    //商品的json字符串
    private String productsJson;

}
