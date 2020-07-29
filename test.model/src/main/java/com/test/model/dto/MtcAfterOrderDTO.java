package com.test.model.dto;

import com.test.model.domain.MtcAfterOrder;
import lombok.Data;

import java.util.Date;

@Data
public class MtcAfterOrderDTO extends MtcAfterOrder {

    private String productName;

    private String productType;

    private Date afterStartDate;

    private Date afterEndDate;



}
