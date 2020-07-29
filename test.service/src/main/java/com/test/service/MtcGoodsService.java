package com.test.service;

import com.test.model.domain.MtcGoods;

public interface MtcGoodsService {

    MtcGoods saveGoods(String type, String name);

    MtcGoods getMtcGoodsById(long gId);

}
