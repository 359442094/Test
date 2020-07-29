package com.test.common.dto;

import lombok.Data;

@Data
public class ProductDTO {

    //商品编号(添加的时候不传,修改的时候必须传)
    private int productId;
    //商品名称
    private String productName;
    //商品型号
    private String productType;

}
