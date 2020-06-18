package com.test.start.test.checkword.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author CJ
 * @date 2020/2/24
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class SensitiveWordFilterRequest implements Serializable {

    private static final long serialVersionUID = 8843939799579926861L;

    @ApiModelProperty(value = "要检查的词语列表",notes = "要检查的词语列表")
    private List<String> words;

}
