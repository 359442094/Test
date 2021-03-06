package com.test.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Getter
@Setter
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = -7135949064887929384L;
    @ApiModelProperty(notes = "用户名称",required = true,position = 2)
    private String userName;
    @ApiModelProperty(notes = "用户密码",required = true,position = 1)
    private String passWord;

}
