package com.test.common.dto;

import lombok.Data;

import java.util.List;

@Data
public class AddProductRequest {

    //{"products":[{"productName":"商品1","productType":"11111"}]}
    //商品列表
    private List<Product> products;

}
