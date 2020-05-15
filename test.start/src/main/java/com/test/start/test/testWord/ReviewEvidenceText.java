package com.test.start.test.testWord;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 人审证据-文本信息
 * @author CJ
 * @date 2020/5/14
 */
@ToString
@Getter
@Setter
public class ReviewEvidenceText {
    /**
     * text	String	人审标注文本
     * reason	String	文本判定原因
     * */
    private String text;

    private String reason;

}
