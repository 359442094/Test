package com.test.start.test.api;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.List;

/**
 * @author chenjie
 * @date 2020-09-22
 */
@Data
public class QueryKingCardListDto extends JSONObject {

    private boolean success;

    private List<LianTongQueryOrderDto> data;

}
