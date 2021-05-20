package com.test.common.dto;

import com.alibaba.fastjson.JSONObject;
import com.test.common.util.MD5HexUtil;
import com.test.common.util.MD5Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {

    public static void main(String[] args) {
        Map<String,String> map=new HashMap<String,String>();
        map.put("name","name");
        map.put("type","type");
        String method = MD5Util.getMd5(JSONObject.toJSONString(map) + "CreateGoods");
        System.out.println("method:"+method);
        /*AddProductRequest request=new AddProductRequest();
        List<Product> products=new ArrayList<>();
        Product product=new Product();
        product.setProductName("商品1");
        product.setProductType("11111");
        products.add(product);
        request.setProducts(products);

        //String jsonString = JSONObject.toJSONString(products);

        String jsonString = "[{'productName':'商品1','productType':'11111'}]";
        List<Product> products1 = JSONObject.parseArray(jsonString, Product.class);
        System.out.println("t:"+products1);*/
    }

}
