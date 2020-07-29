package com.test.service.impl;

import com.test.model.domain.MtcAfterOrderDetail;
import com.test.model.domain.MtcAfterOrderDetailExample;
import com.test.model.persistence.MtcAfterOrderDetailMapper;
import com.test.service.MtsAfterOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MtsAfterOrderDetailServiceImpl implements MtsAfterOrderDetailService {

    @Autowired
    private MtcAfterOrderDetailMapper mtcAfterOrderDetailMapper;

    @Override
    public void saveAfterOrderDetail(MtcAfterOrderDetail detail) {
        mtcAfterOrderDetailMapper.insert(detail);
    }

    @Override
    public MtcAfterOrderDetail getAfterOrderDetailById(long afterOrderDetailId) {
        MtcAfterOrderDetailExample example=new MtcAfterOrderDetailExample();
        example.createCriteria().andAfterOrderIdEqualTo(afterOrderDetailId);
        List<MtcAfterOrderDetail> orderDetails = mtcAfterOrderDetailMapper.selectByExample(example);
        return orderDetails.size()>0?orderDetails.get(0):null;
    }

}
