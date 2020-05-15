package com.test.start.test.testWord;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 机器检查的图片信息
 * @author CJ
 * @date 2020/5/14
 */
@ToString
@Getter
@Setter
public class EvidenceImage {
    /**
     * taskId	String	当前图片检测数据标识
     * sequence	Number	当前图片在原始文档中的序号
     * imageUrl	String	图片下载地址
     * level	Number	分类级别，0：正常，1：不确定，2：确定
     * labels	json对象数组	分类信息，100：色情，110：性感低俗，200：广告，210：二维码，300：暴恐，400：违禁，500：涉政，900：其他
     * */
    private String taskId;

    private int sequence;

    private String imageUrl;

    private int level;

    private List<Labels> labels;
}
