package com.test.start.test.testWord;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 机器检查的文本信息
 * @author CJ
 * @date 2020/5/14
 */
@ToString
@Getter
@Setter
public class EvidenceText {

    /**
     * 当前分段检测数据标识
     * */
    private String taskId;
    /**
     * 当前文本在原始文档中的分段（5000字符/段）序号
     * */
    private int sequence;
    /**
     * 每段文本的开始20个字符
     * */
    private String startText;
    /**
     * 每段文本的结尾20个字符
     * */
    private String endText;
    /**
     * 检测结果，0：通过，1：嫌疑，2：不通过
     * */
    private int action;
    /**
     * 分类信息
     * */
    private Labels[] labels;

}
