package com.test.controller;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
public class NewLeads implements Serializable {

    private String leadsId;
    @Excel(name = "机构编号")
    private String orgId;
    @Excel(name = "机构名称")
    private String orgName;
    private String phone;
    private String name;
    private String createType;
    @Excel(name = "时间",format = "yyyy-MM-dd")
    private Date createTime;

    private Date startDate;

    private Date endDate;

}
