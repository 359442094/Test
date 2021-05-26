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
public class CheckFileResponse {

    /**
     * 接口调用状态，200:正常
     * 400	bad request	请求缺少 secretId 或 businessId
     * 401	forbidden	businessId无效或过期，请按照如下步骤排查：(1)试用时间为7天，试用到期请联系商务 (2)检测业务Id是否正确 (3)检测密钥信息是否正确 (4)检测请求接口是否匹配
     * 405	param error	请求参数异常，请检查参数是否有缺失，参数是否正确，如果检查无误请您及时联系我们。
     * 410	signature failure	签名验证失败，目前请求参数仅支持form表单形式，建议content-type设置为 application/x-www-form-urlencoded 编码格式
     * 411	high frequency	请求频率超过限制（QPS，文本默认200条/s，图片64张/s，视频直播100路/s，视频点播100路/s，直播电视墙100路/s，点播电视墙100路/s，直播音频100路/s，点播音频100路/s）
     * 413	trial style amount over limit	试用期请求数据总量超过限制（试用期间，文本上限8W，图片上限5W，视频直播/点播上限500路，直播/点播电视墙上限100路，直播/点播音频上限500路）
     * 414	param len over limit	请求长度/大小超过限制
     * 415	target version limit	业务版本限制
     * 420	request expired	请求过期
     * 430	replay attack	重放攻击
     * 503	service unavailable	接口异常
     */
    private int code;
    /**
     * 结果说明，如果接口调用出错，那么返回错误描述，成功返回 ok
     */
    private String msg;
    /**
     * 返回内容
     */
    private List<CheckFileReult> result;

}
