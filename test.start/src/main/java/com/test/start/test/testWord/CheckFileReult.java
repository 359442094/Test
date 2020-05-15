package com.test.start.test.testWord;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author CJ
 * @date 2020/5/14
 */
@ToString
@Getter
@Setter
public class CheckFileReult {
    /**
     * 数据唯一标识
     * */
    private String dataId;
    /**
     * 文档数据请求标识
     * */
    private String taskId;
    /**
     * 产品调用文本在线检测传递的 callback 字段数据
     * */
    private String callback;
    /**
     * 机器检测结果,
     * 1:正常（建议通过）
     * 2:异常（建议拦截）
     * 3：疑似（建议人工确认）
     * 0:无结果（检测失败）
     * */
    private int result;
    /**
     * 人审证据信息，接入人工审核后，参考人审证据信息，人审证据信息与机器检测证据信息不共存
     * */
    private CheckFileReviewEvidence reviewEvidences;
    /**
     * 检测失败原因
     * failureReason 说明
     * 错误码	描述
     * 1000	文档大小超过上限
     * 1001	文档格式不支持
     * 1002	文档下载失败
     * 2000	文档内容提取失败
     * 2001	文档内容提取超时
     * 3000	文档检测失败
     * 3001	文档文本检测失败
     * 3002	文档图片检测失败
     * 3003	检测超时
     * */
    private int failureReason;
    /**
     * 机器证据信息
     * */
    private CheckFileEvidence evidences;

    private int resultType;

}
