package com.test.service.impl;

import com.test.model.domain.MtcGoods;
import com.test.model.persistence.MtcGoodsMapper;
import com.test.service.MtcGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class MtcGoodsServiceImpl implements MtcGoodsService {

    @Autowired
    private MtcGoodsMapper mtcGoodsMapper;

    @Override
    public MtcGoods saveGoods(String type, String name) {
        MtcGoods mtcGoods=new MtcGoods();
        mtcGoods.setGoodNum(UUID.randomUUID().toString());
        mtcGoods.setGoodBarcode("");
        mtcGoods.setGoodName(name);
        mtcGoods.setModel(type);
        mtcGoods.setCreationDate(new Date());
        mtcGoods.setLastUpdateDate(new Date());
        mtcGoods.setCreatedBy((long) 1);
        mtcGoods.setLastUpdatedBy((long) 1);
        mtcGoods.setEnableFlag(true);
        mtcGoods.setTenantId((long) 1);
        mtcGoods.setObjectVersionNumber((long) -1);
        int insert = mtcGoodsMapper.insert(mtcGoods);
        return insert>0?mtcGoods:null;
    }

    @Override
    public MtcGoods getMtcGoodsById(long gId) {
        return mtcGoodsMapper.selectByPrimaryKey(gId);
    }

}
