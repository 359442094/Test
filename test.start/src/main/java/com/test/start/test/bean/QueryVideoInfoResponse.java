package com.test.start.test.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author CJ
 * @date 2019/10/22
 */
@ToString
@Getter
@Setter
public class QueryVideoInfoResponse extends VideoBaseResponse implements Serializable {

    private static final long serialVersionUID = 1496020666836084605L;
    @ApiModelProperty(value = "视频分类目录数据",notes = "视频分类目录数据")
    private List<APINode> data=new ArrayList<>();

}
