package com.test.start.test.testWord;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 线索详细信息
 * @author CJ
 * @date 2020/5/14
 */
@ToString
@Getter
@Setter
public class HitInfo {
    /**
     * hitType	Number	线索分类信息，
     * 返回10:表示命中用户自定义添加用户名单，
     * 返回11:表示命中用户自定义添加ip名单，
     * 返回30:表示命中用户自定义添加敏感词
     * */
    private int hitType;
}
