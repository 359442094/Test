package com.test.common.videoApi.impl;

import com.test.common.videoApi.MergeRecordFileCallBackAPI;
import com.test.common.videoApi.MergeRecordFileCallBackRequest;
import com.test.common.videoApi.MergeRecordFileCallBackResopnse;
import org.springframework.stereotype.Component;

/**
 * @author CJ
 * @date 2019/10/24
 */
@Component
public class MergeRecordFileCallBackAPIImpl implements MergeRecordFileCallBackAPI {
    @Override
    public MergeRecordFileCallBackResopnse process(Integer id,MergeRecordFileCallBackRequest request) {
        MergeRecordFileCallBackResopnse resopnse=new MergeRecordFileCallBackResopnse();
        resopnse.setId(id);
        resopnse.setFileId(request.getFileId());
        resopnse.setFileIds(request.getFileIds());
        resopnse.setFileName(request.getFileName());
        resopnse.setFileUrl(request.getFileUrl());
        return resopnse;
    }
}
