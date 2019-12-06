package com.test.start.test.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 智能录播精编版上传视频信息的上传者信息
 * @author CJ
 * @date 2019/12/5
 */
@ToString
@Getter
@Setter
public class APIUploader implements Serializable {

    private static final long serialVersionUID = -2189553844389656598L;
    @ApiModelProperty(value = "名称",notes = "名称")
    private String name;
    @ApiModelProperty(value = "邮箱",notes = "邮箱")
    private String email;
    @ApiModelProperty(value = "角色",notes = "角色")
    private String role;

}
