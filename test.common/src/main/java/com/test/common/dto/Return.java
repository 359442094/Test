package com.test.common.dto;

import lombok.*;
import org.springframework.util.StringUtils;

@ToString
@Getter
@Setter
public class Return<T> {

    private String code;

    private String mess;

    private T data;

    public Return(T data) {
        if(StringUtils.isEmpty(data)){
            this.code = "000";
            this.mess = "未获取到相关数据";
            this.data = data;
        }else{
            this.code = "200";
            this.mess = "已正常响应数据结果";
            this.data = data;
        }
    }

    public Return(String code, String mess, T data) {
        this.code = code;
        this.mess = mess;
        this.data = data;
    }
}
