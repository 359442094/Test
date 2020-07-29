package com.test.service;

import com.test.model.domain.MtcAfterOrderDetail;

public interface MtsAfterOrderDetailService {

    void saveAfterOrderDetail(MtcAfterOrderDetail detail);

    MtcAfterOrderDetail getAfterOrderDetailById(long afterOrderDetailId);

}
