package com.test.common.videoApi;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author CJ
 * @date 2019/10/24
 */
@ToString
@Getter
@Setter
public class MergeRecordFileCallBackResopnse extends BaseResponse implements Serializable {

    private static final long serialVersionUID = 4660129178058658487L;

    @ApiModelProperty(value = "合并前的文件ID",notes = "合并前的文件ID")
    private String fileIds;
    @ApiModelProperty(value = "合并后的文件ID",notes = "合并后的文件ID")
    private String fileId;
    @ApiModelProperty(value = "合并后的m3u8的地址",notes = "合并后的m3u8的地址")
    private String fileUrl;
    @ApiModelProperty(value = "合并后的文件名称",notes = "合并后的文件名称")
    private String fileName;

    private Integer id;

}
