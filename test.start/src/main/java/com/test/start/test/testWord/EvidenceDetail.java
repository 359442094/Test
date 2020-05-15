package com.test.start.test.testWord;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 机器审核其他信息
 * @author CJ
 * @date 2020/5/14
 */
@ToString
@Getter
@Setter
public class EvidenceDetail {
    /**
     * hint	json数组	线索信息，用于定位文本中有问题的部分，辅助人工审核
     * hitInfos	json数组	线索详细信息
     * */

    private Object hint;

    private HitInfo[] hitInfos;

}
