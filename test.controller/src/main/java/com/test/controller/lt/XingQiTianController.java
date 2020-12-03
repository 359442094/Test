package com.test.controller.lt;

import com.test.common.annoation.ShowLogger;
import com.test.common.dto.RechargeOrderSubmitCallBackRequest;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 话费文档地址
 * http://doc.bm724.com/apidoc/index?id=12
 *   urlPrefix:http://apib.bm724.com
 *   id:111179
 * 联通话费充值+查询(异步|实时)处理
 * @author chenjie
 * @date 2020-11-18
 */
@RestController
public class XingQiTianController {

    //星启天充值话费接口-异步回调
    @ApiOperation(value = "星启天充值话费接口-异步回调",notes = "星启天充值话费接口-异步回调")
    @ShowLogger(info = "星启天充值话费接口-异步回调")
    @RequestMapping(path = "/rechargeOrderSubmitCallBack",method = RequestMethod.POST)
    public RechargeOrderSubmitCallBackRequest rechargeOrderSubmitCallBack(RechargeOrderSubmitCallBackRequest request){
        return request;
    }

}
