package com.test.start.test.bean;

import com.test.common.videoApi.BaseResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author CJ
 * @date 2019/12/3
 */
@ToString
@Getter
@Setter
public class APICata extends BaseResponse implements Serializable {

    private static final long serialVersionUID = -1323611386687109996L;

    @ApiModelProperty(value = "上级分类编号,当前分类编号",notes = "上级分类编号,当前分类编号")
    private String catatree;
    @ApiModelProperty(value = "分类编号",notes = "分类编号")
    private String cataid;

}
