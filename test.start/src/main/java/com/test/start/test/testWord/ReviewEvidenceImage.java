package com.test.start.test.testWord;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 人审证据-图片信息
 * @author CJ
 * @date 2020/5/14
 */
@ToString
@Getter
@Setter
public class ReviewEvidenceImage {
    /**
     * url	String	人审标注图片url
     * reason	String	图片判定原因
     * */

    private String url;

    private String reason;

}
