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
@ApiModel(value = "test")
public class Test implements Serializable {

    private static final long serialVersionUID = 8268362201615665826L;
    @ApiModelProperty(value = "test姓名",notes = "test姓名")
    private String name;

}
