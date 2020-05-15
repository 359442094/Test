package com.test.start.test.testWord;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 人审证据信息，接入人工审核后，参考人审证据信息，人审证据信息与机器检测证据信息不共存
 * @author CJ
 * @date 2020/5/14
 */
@ToString
@Getter
@Setter
public class CheckFileReviewEvidence {
    /**
     * reason	String	判定原因
     * remark	String	备注信息
     * evidenceDetail	json对象数组	人审证据详细信息
     * */
    private String reason;

    private String remark;

    private List<ReviewEvidenceDetail> evidenceDetail;

}
