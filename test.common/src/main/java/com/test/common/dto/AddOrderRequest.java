package com.test.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class AddOrderRequest {

    //联系人
    private String name;
    //性别(0:男 1:女11)
    private int sex;
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
    //预约时间
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;
    //服务人员编号
    private int serviceUserId;
    //服务人手机号码
    private String serviceUserPhone;
    //商品的json字符串
    private String productsJson;

}
