package com.test.common.dto;
/**
 * @date：2020/4/22
 * @author：Beason <dianzhang.zhou@hand-china.com>
 * @version：0.0.1
 * @copyright Copyright (C) 2020, Hand
 */

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

//import javax.validation.constraints.NotBlank;

/**
 * @ClassName queryDto
 * @Description TODO
 * @Author Beason
 * @Date 2020/4/22 16:41
 * @Email dianzhang.zhou@hand-china.com
 **/
@Data
public class QueryDTO {
    @ApiModelProperty(value = "租户ID")
    private Long organizationId;
    @ApiModelProperty(value = "请求方法名称")
    //@NotBlank
    private String method;
    @ApiModelProperty(value = "签名")
    //@NotBlank
    private String sign;
    @ApiModelProperty(value = "微信用户唯一标识")
    private String openId;
    @ApiModelProperty(value = "请求参数")
    private QueryObjectDTO rest_data;
}
