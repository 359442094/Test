package com.test.common.dto;/**
 * @date：2020/4/22
 * @author：Beason <dianzhang.zhou@hand-china.com>
 * @version：0.0.1
 * @copyright Copyright (C) 2020, Hand
 */
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * @ClassName queryObjectDto
 * @Description TODO
 * @Author Beason
 * @Date 2020/4/22 16:40
 * @Email dianzhang.zhou@hand-china.com
 **/
@Getter
public class QueryObjectDTO {
    @ApiModelProperty(value = "page")
    private int page;
    @ApiModelProperty(value = "page size")
    private int pageSize;
    @ApiModelProperty(value = "order by")
    private String orderBy;
    @ApiModelProperty(value = "请求参数集合")
    private List<Map<String,String>> param;

    public void setPage(int page) {
        this.page = page - 1;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public void setParam(List<Map<String,String>> param) {
        this.param = param;
    }
}
