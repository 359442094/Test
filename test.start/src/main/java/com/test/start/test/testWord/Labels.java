package com.test.start.test.testWord;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 分类信息列表
 * @author CJ
 * @date 2020/5/14
 */
@ToString
@Getter
@Setter
public class Labels {

    /**
     * label	Number	分类信息，100：色情，200：广告，300：暴恐，400：违禁，500：涉政，600：谩骂，700：灌水，900：其他
     * subLabels	json数组	细分类信息，可能包含多个，可能为空
     * level	Number	分类级别，0：通过， 1：嫌疑，2：不通过
     * evidenceDetails	json对象	其他信息
     * */

    private int label;

    private SubLabel[] subLabels;

    private int level;

    private EvidenceDetail details;

    private double rate;

}
