package com.test.controller;

import com.test.common.annoation.Excel;
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

    private String orgId;
    @Excel(name = "所属机构")
    private String orgName;
    @Excel(name = "手机")
    private String phone;
    @Excel(name = "姓名")
    private String name;
    @Excel(name = "来源")
    private String createType;
    @Excel(name = "入库时间",dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private Date startDate;

    private Date endDate;

}
