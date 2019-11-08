package com.test.common.videoApi.impl;

import com.test.common.videoApi.FileForwardSaveCallBackAPI;
import com.test.common.videoApi.FileForwardSaveCallBackRequest;
import com.test.common.videoApi.FileForwardSaveCallBackResopnse;
import org.springframework.stereotype.Component;

/**
 * @author CJ
 * @date 2019/10/24
 */
@Component
public class FileForwardSaveCallBackAPIImpl implements FileForwardSaveCallBackAPI {
    @Override
    public FileForwardSaveCallBackResopnse process(FileForwardSaveCallBackRequest request) {
        FileForwardSaveCallBackResopnse resopnse=new FileForwardSaveCallBackResopnse();
        resopnse.setStatus(request.getStatus());
        return resopnse;
    }
}
