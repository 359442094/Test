package com.test.start.test.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author CJ
 * @date 2019/10/23
 */
@ToString
@Getter
@Setter
public class QueryTranscribeVideoResponse extends VideoBaseResponse implements Serializable {

    private static final long serialVersionUID = -6490795502875989754L;
    @ApiModelProperty(value = "录制文件",notes = "录制文件")
    private List<APIRecordFile> data;

}
