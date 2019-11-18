package com.test.common.videoApi.impl;

import com.test.common.videoApi.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CJ
 * @date 2019/10/24
 */
@Slf4j
@Component
public class MergeRecordFileCallBackAPIImpl implements MergeRecordFileCallBackAPI {

    //回调访问前缀
    @Value(value = "${xiaoYiService.callBackPrefix}")
    private String callBackPrefix;
    //同步至点播Url
    @Value(value = "${xiaoYiService.forwardRecordCallBack}")
    private String forwardRecordCallBack;
    //合并视频Url
    @Value(value = "${xiaoYiService.mergeRecordCallBack}")
    private String mergeRecordCallBack;

    @Autowired
    private FileForwardSaveAPI fileForwardSaveAPI;

    @Override
    public MergeRecordFileCallBackResopnse process(Integer id,MergeRecordFileCallBackRequest request) {
        log.info("合并回调函数");
        MergeRecordFileCallBackResopnse resopnse=new MergeRecordFileCallBackResopnse();
        resopnse.setId(id);
        resopnse.setFileId(request.getFileId());
        resopnse.setFileIds(request.getFileIds());
        resopnse.setFileName(request.getFileName());
        resopnse.setFileUrl(request.getFileUrl());
        List<String> fileIds=new ArrayList<>();
        fileIds.add(request.getFileId());
        log.info("同步点播上送参数:" + getForwardRequest(id,"383452",fileIds));
        FileForwardSaveResponse response1 = fileForwardSaveAPI.process(getForwardRequest(id, "383452", fileIds));
        log.info("转存录制文件到点播结果:" + response1);
        return resopnse;
    }

    public FileForwardSaveRequest getForwardRequest(Integer id, String channelId, List<String> fileIds) {
        FileForwardSaveRequest forwardRequest = new FileForwardSaveRequest();
        //回调函数带上录播课程编号
        forwardRequest.setCallbackUrl(callBackPrefix + forwardRecordCallBack + "?id=" + id);
        forwardRequest.setChannelId(channelId);
        forwardRequest.setFileIds(fileIds);
        return forwardRequest;
    }

}
