package com.test.start.test.checkword.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author CJ
 * @date 2020/2/24
 */
@ToString
@Getter
@Setter
public class SensitiveWordFilterResponse implements Serializable {

    private static final long serialVersionUID = 8881125522829739241L;

    @ApiModelProperty(value = "检查结果列表",notes = "检查结果列表")
    private List<SensitiveWordFilter> results;

    @ToString
    @Getter
    @Setter
    public class SensitiveWordFilter{

        @ApiModelProperty(value = "检查内容",notes = "检查内容")
        private String checkWord;
        @ApiModelProperty(value = "检查结果",notes = "true:正常 false:异常")
        private boolean flag;
        @ApiModelProperty(value = "检查结果反馈的信息",notes = "成功/失败信息")
        private String message;
        @ApiModelProperty(value = "检测到的敏感词内容",notes = "为空:正常 不为空:异常")
        private String textFilter;

    }

}
