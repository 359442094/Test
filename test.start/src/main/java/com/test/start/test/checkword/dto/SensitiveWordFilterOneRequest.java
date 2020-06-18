package com.test.start.test.checkword.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * @author CJ
 * @date 2020/2/24
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class SensitiveWordFilterOneRequest implements Serializable {

    private static final long serialVersionUID = 14699182728626782L;

    @ApiModelProperty(value = "要检查的词语",notes = "要检查的词语",required = true)
    private String word;

}
