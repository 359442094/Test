package com.test.controller;

import com.test.common.dto.AddOrderRequest;
import com.test.common.dto.ApplyForAfterOrderDTO;
import com.test.model.dto.MtcAfterOrderDTO;
import com.test.model.domain.MtcAfterOrder;
import com.test.model.domain.MtcAfterOrderDetail;
import com.test.model.domain.MtcGoods;
import com.test.model.dto.QueryShopOrderDTO;
import com.test.service.FlushIndexService;
import com.test.service.MtcGoodsService;
import com.test.service.MtsAfterOrderDetailService;
import com.test.service.MtsAfterOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Api(tags = {"璞康接口"})
@RestController
@Slf4j
public class PookQueryController {

    @Autowired
    private FlushIndexService flushIndexService;
    @Autowired
    private MtcGoodsService mtcGoodsService;
    @Autowired
    private MtsAfterOrderService mtsAfterOrderService;
    @Autowired
    private MtsAfterOrderDetailService mtsAfterOrderDetailService;

    @ApiOperation(value = "璞康测试")
    @RequestMapping(path = "/test/pook",method = RequestMethod.GET)
    public Object test() throws Exception {
        String result = flushIndexService.flushLuceneIndex();
        return result;
    }

    @ApiOperation(value = "璞康添加商品")
    @RequestMapping(path = "/test/saveGoods",method = RequestMethod.POST)
    public Object addGroods(@RequestParam String type,@RequestParam String name) throws Exception {
        MtcGoods mtcGoods = mtcGoodsService.saveGoods(type, name);
        return mtcGoods;
    }

    @ApiOperation(value = "璞康添加订单")
    @RequestMapping(path = "/test/addOrder",method = RequestMethod.POST)
    public Object addGroods(@RequestBody AddOrderRequest request) throws Exception {
        boolean saveGoods = mtsAfterOrderService.saveOrder(request);
        return saveGoods;
    }

    @ApiOperation(value = "璞康申请订单json测试")
    @RequestMapping(path = "/test/addOrderJsonTest",method = RequestMethod.POST)
    public void addOrderJsonTest(@RequestBody ApplyForAfterOrderDTO dto) throws Exception {
        //获取的商品编号
        long pId = (long)dto.getProductId();
        MtcGoods goods = mtcGoodsService.getMtcGoodsById(pId);
        //添加售后订单
        MtcAfterOrder order=new MtcAfterOrder();
        //售后订单编码
        String afterOrderNum = UUID.randomUUID().toString();
        order.setAfterOrderNum(afterOrderNum);
        //服务类型(0,安装，1维修，2配件更换)
        order.setServiceTypeCode(dto.getServiceType());

        order.setMasterMobile("");
        //售后状态(-1 新建状态,0审核通过,1驳回，2服务中，3已完成,4已关闭)
        order.setOrderStatus("-1");
        order.setMemberId((long)0);
        order.setContactor(dto.getName());
        order.setContactorSex(true);
        order.setMobile(dto.getPhone());

        order.setRegionCountryId("中国");
        order.setRegionProviceId(dto.getProvince());
        order.setRegionCityId(dto.getCity());
        order.setRegionDistrictId(dto.getDistrict());
        order.setRegionAddress(dto.getAddress());
        order.setAfterDescriptions(dto.getShopDesc());
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
        mtsAfterOrderService.saveOrder(order);
        //添加售后订单详情
        MtcAfterOrderDetail detail=new MtcAfterOrderDetail();
        detail.setAfterOrderNum(order.getAfterOrderNum());
        detail.setAfterOrderId((long)1);

        detail.setGoodId(goods.getGoodId());
        detail.setGoodNum(goods.getGoodNum());
        detail.setUnitCode(goods.getUnitCode());
        detail.setPrice(goods.getPrice());
        detail.setQuantity(goods.getQuantity());
        detail.setReserveStartDate(dto.getServiceStartDate());
        detail.setReserveEndDate(dto.getServiceEndDate());

        detail.setCreatedBy(userId);
        detail.setLastUpdatedBy(userId);
        detail.setCreationDate(date);
        detail.setLastUpdateDate(date);

        detail.setEnableFlag(true);
        detail.setTenantId((long)-1);
        detail.setObjectVersionNumber((long)-1);
        mtsAfterOrderDetailService.saveAfterOrderDetail(detail);
    }

    @ApiOperation(value = "售后列表",notes = "售后列表")
    @ResponseBody
    @RequestMapping(path = "/test/productList",method = RequestMethod.GET)
    public List<MtcAfterOrder> productList(){
        return mtsAfterOrderService.product();
    }

    @ApiOperation(value = "售后订单详情",notes = "售后订单审核")
    @ResponseBody
    @RequestMapping(path = "/test/product/{afterOrderId}/detail",method = RequestMethod.GET)
    public MtcAfterOrderDTO detail(@PathVariable int afterOrderId){
        MtcAfterOrderDTO orderDtoById = mtsAfterOrderService.getOrderDtoById(afterOrderId);
        return orderDtoById;
    }

    @ApiOperation(value = "售后订单审核接口",notes = "售后订单审核接口")
    @ResponseBody
    @RequestMapping(path = "/test/product/{afterOrderId}/aduit",method = RequestMethod.GET)
    public MtcAfterOrder aduit(@PathVariable int afterOrderId,@RequestParam String orderStatus){
        MtcAfterOrder afterOrder = mtsAfterOrderService.getOrderById(afterOrderId);
        if(StringUtils.isEmpty(afterOrder)){
            log.error("编号为:["+afterOrderId+"]"+"的售后订单信息不存在");
        }
        afterOrder.setOrderStatus(orderStatus);
        afterOrder = mtsAfterOrderService.editOrderById(afterOrder);
        return afterOrder;
    }

}
