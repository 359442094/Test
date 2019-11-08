package com.test.common.videoApi;

/**
 * 异步批量转存录制文件到点播回调函数
 * @author CJ
 * @date 2019/10/24
 */
public interface FileForwardSaveCallBackAPI {

    FileForwardSaveCallBackResopnse process(FileForwardSaveCallBackRequest request);

}
