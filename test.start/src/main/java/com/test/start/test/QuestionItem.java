package com.test.start.test;

import lombok.*;

/**
 * 试题选项
 * @author CJ
 * @date 2020/5/7
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class QuestionItem {

    private Integer itemId;
    //关联的试题编号
    private String questionId;
    //选项序号
    private String itemIndex;
    //选项字母
    private String itemLetter;
    //选项描述
    private String text;
    //是否为正确答案
    private boolean flag;

    public QuestionItem(Integer itemId,String questionId, String text, boolean flag) {
        this.itemId=itemId;
        this.questionId=questionId;
        this.text = text;
        this.flag = flag;
    }

}
