package com.test.start.test;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 试题
 * @author CJ
 * @date 2020/5/7
 */
@ToString
@Getter
@Setter
public class Question {

    //试题编号
    private Integer questionId;
    //试题类型
    private String questionType;
    //试题标题
    private String questionTitle;
    //选项序号
    private String questionIndex;
    //选项字母
    private String questionLetter;
    //试题选项
    private List<QuestionItem> questionItems;

    public Question(Integer questionId, String questionType, String questionTitle, List<QuestionItem> questionItems) {
        this.questionId = questionId;
        this.questionType = questionType;
        this.questionTitle = questionTitle;
        this.questionItems = questionItems;
    }

    public Question() {}
}
