package com.test.service;

import com.test.common.dto.AddOrderRequest;
import com.test.model.dto.MtcAfterOrderDTO;
import com.test.model.dto.QueryShopOrderDTO;
import com.test.model.domain.MtcAfterOrder;

import java.util.List;

public interface MtsAfterOrderService {

    boolean saveOrder(AddOrderRequest request);

    MtcAfterOrder saveOrder(MtcAfterOrder order);

    List<MtcAfterOrder> product();

    MtcAfterOrder getOrderById(long afterOrderId);

    MtcAfterOrder editOrderById(MtcAfterOrder afterOrder);

    MtcAfterOrderDTO getOrderDtoById(int afterOrderId);

}
