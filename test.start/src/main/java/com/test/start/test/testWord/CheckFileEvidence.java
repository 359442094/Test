package com.test.start.test.testWord;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 机器检查结果
 * @author CJ
 * @date 2020/5/14
 */
@ToString
@Getter
@Setter
public class CheckFileEvidence {
    /**
     * evidenceTexts	json对象数组	文本证据信息
     * evidenceImages	json对象数组	图片证据信息
     * */
    private EvidenceText[] texts;

    private EvidenceImage[] images;

}
