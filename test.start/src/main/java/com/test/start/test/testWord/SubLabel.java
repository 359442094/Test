package com.test.start.test.testWord;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 细分类
 * @author CJ
 * @date 2020/5/14
 */
@ToString
@Getter
@Setter
public class SubLabel {
    /**
     * subLabel	Number	细分类，详细编码请参考下方对应细分类编码对照表
     * 100001	色情其他
     * 100002	色情传播
     * 100003	色情性器官
     * 100004	色情挑逗
     * 100005	色情低俗段子
     * 100006	色情性行为
     * 100007	色情舆情事件
     * 100008	色情交友类
     * 200009	商业推广
     * 200010	广告法
     * 200011	刷量行为
     * 200012	广告其他
     * 300016	暴恐其他
     * 400017	违禁其他
     * 500013	涉政其他
     * 500014	涉政专项
     * 500015	严格涉政
     * 600018	谩骂其他
     * 700019	灌水其他
     * 900020	其他
     * */

    private int subLabel;

}
