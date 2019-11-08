package com.test.common.dto;

import com.test.common.annoation.CheckField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author CJ
 * @date 2019/10/30
 */
@ToString
@Getter
@Setter
public class CheckFieldRequest implements Serializable {

    private static final long serialVersionUID = -2094155115530633850L;

    @ApiModelProperty(value = "编号",notes = "编号")
    @CheckField(notNull = false)
    public String id;

    @ApiModelProperty(value = "姓名",notes = "姓名")
    @CheckField(notNull = true)
    public String name;

}
