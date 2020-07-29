package com.test.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.test.common.dto.AddOrderRequest;
import com.test.model.dto.MtcAfterOrderDTO;
import com.test.common.dto.Product;
import com.test.model.domain.*;
import com.test.model.dto.QueryShopOrderDTO;
import com.test.model.persistence.MtcAfterOrderDetailMapper;
import com.test.model.persistence.MtcAfterOrderMapper;
import com.test.service.MtcGoodsService;
import com.test.service.MtsAfterOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class MtsAfterOrderServiceImpl implements MtsAfterOrderService {

    @Autowired
    private MtcAfterOrderMapper afterOrderMapper;
    @Autowired
    private MtcAfterOrderDetailMapper afterOrderDetailMapper;
    @Autowired
    private MtcGoodsService mtcGoodsService;

    @Override
    public boolean saveOrder(AddOrderRequest request) {
        MtcAfterOrder order=new MtcAfterOrder();
        //售后订单编码
        String afterOrderNum = UUID.randomUUID().toString();
        order.setAfterOrderNum(afterOrderNum);
        //服务类型(0,安装，1维修，2配件更换)
        order.setServiceTypeCode(request.getServiceType());

        order.setMasterMobile(request.getServiceUserPhone());
        //售后状态(-1 新建状态,0审核通过,1驳回，2服务中，3已完成,4已关闭)
        order.setOrderStatus("-1");
        order.setMemberId((long)request.getServiceUserId());
        order.setContactor(request.getName());
        order.setMobile(request.getPhone());

        order.setRegionCountryId("中国");
        order.setRegionProviceId(request.getProvince());
        order.setRegionCityId(request.getCity());
        order.setRegionDistrictId(request.getDistrict());
        order.setRegionAddress(request.getAddress());
        //创建人 根据openId
        long userId = 1;
        Date date = new Date();
        order.setCreatedBy(userId);
        order.setLastUpdatedBy(userId);
        order.setCreationDate(date);
        order.setLastUpdateDate(date);

        order.setEnableFlag(true);
        order.setTenantId((long)-1);
        order.setObjectVersionNumber((long)-1);
        int insert = afterOrderMapper.insert(order);

        String productsJson = request.getProductsJson();
        if(!StringUtils.isEmpty(productsJson)){
            List<Product> products= JSONObject.parseArray(productsJson, Product.class);
            for (Product product : products) {
                //添加商品信息
                MtcGoods mtcGoods = mtcGoodsService.saveGoods(product.getProductType(), product.getProductName());
                //添加售后订单商品详情信息
                MtcAfterOrderDetail detail=new MtcAfterOrderDetail();
                detail.setAfterOrderId(order.getAfterOrderId());
                detail.setAfterOrderNum(afterOrderNum);
                detail.setGoodId(mtcGoods.getGoodId());
                detail.setGoodNum(mtcGoods.getGoodNum());
                detail.setEnableFlag(true);
                detail.setTenantId((long)-1);
                detail.setObjectVersionNumber((long)-1);
                detail.setCreatedBy(userId);
                detail.setLastUpdatedBy(userId);
                detail.setCreationDate(date);
                detail.setLastUpdateDate(date);
                afterOrderDetailMapper.insert(detail);
            }

        }

        return insert>0?true:false;
    }

    @Override
    public MtcAfterOrder saveOrder(MtcAfterOrder order) {
        int insert = afterOrderMapper.insert(order);
        return insert>0?order:null;
    }

    @Override
    public List<MtcAfterOrder> product() {
        List<MtcAfterOrder> queryShopOrderDTOS = afterOrderMapper.productList();
        return queryShopOrderDTOS;
    }

    @Override
    public MtcAfterOrder getOrderById(long afterOrderId) {
        return afterOrderMapper.selectByPrimaryKey(afterOrderId);
    }

    @Override
    public MtcAfterOrder editOrderById(MtcAfterOrder afterOrder) {
        int update = afterOrderMapper.updateByPrimaryKey(afterOrder);
        return update>0?afterOrder:null;
    }

    @Override
    public MtcAfterOrderDTO getOrderDtoById(int afterOrderId) {
        return afterOrderMapper.getOrderDtoById(afterOrderId);
    }

}
