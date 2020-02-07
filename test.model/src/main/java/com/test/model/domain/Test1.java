package com.test.model.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @author CJ
 * @date 2019/12/18
 */
@ToString
@Getter
@Setter
@Slf4j
@ApiModel(value = "test1")
public class Test1 implements Serializable {

    private static final long serialVersionUID = -3093546341405867911L;

    @ApiModelProperty(value = "test1姓名",notes = "test1姓名")
    private String name;
    @ApiModelProperty(value = "test1value",notes = "test1value")
    private String value;

}
