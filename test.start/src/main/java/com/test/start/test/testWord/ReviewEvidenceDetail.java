package com.test.start.test.testWord;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 人审记录详情列表
 * @author CJ
 * @date 2020/5/14
 */
@ToString
@Getter
@Setter
public class ReviewEvidenceDetail {

    /**
     * texts	json对象数组	文本证据信息
     * images	json对象数组	图片证据信息
     * */
    private List<ReviewEvidenceText> tests;

    private List<ReviewEvidenceImage> images;

}
