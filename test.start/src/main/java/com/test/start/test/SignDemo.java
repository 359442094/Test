package com.test.start.test;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.imageaudit.model.v20191230.ScanImageResponse;

import java.util.List;
import java.util.stream.Collectors;

public class SignDemo {
    /**
     * pass：图片正常，无需进行其余操作；或者未识别出目标对象。
     * review：检测结果不确定，需要进行人工审核；或者未识别出目标对象。
     * block：图片违规，建议执行进一步操作（如直接删除或做限制处理）。
     * */
    public static void main(String[] args) throws Exception {

        String json="{\"data\":{\"results\":[{\"dataId\":\"af98e0ca-2f9a-4748-bebe-b538f3a0a9bc\",\"imageURL\":\"http://94.191.62.87:81/file/yellow.png\",\"subResults\":[{\"frames\":[],\"hintWordsInfoList\":[],\"label\":\"porn\",\"logoDataList\":[],\"oCRDataList\":[],\"programCodeDataList\":[],\"rate\":78.759995,\"scene\":\"porn\",\"sfaceDataList\":[],\"suggestion\":\"review\"},{\"frames\":[],\"hintWordsInfoList\":[],\"label\":\"normal\",\"logoDataList\":[],\"oCRDataList\":[],\"programCodeDataList\":[],\"rate\":99.95,\"scene\":\"terrorism\",\"sfaceDataList\":[],\"suggestion\":\"pass\"},{\"frames\":[],\"hintWordsInfoList\":[],\"label\":\"normal\",\"logoDataList\":[],\"oCRDataList\":[],\"programCodeDataList\":[],\"rate\":99.9,\"scene\":\"ad\",\"sfaceDataList\":[],\"suggestion\":\"pass\"},{\"frames\":[],\"hintWordsInfoList\":[],\"label\":\"normal\",\"logoDataList\":[],\"oCRDataList\":[],\"programCodeDataList\":[],\"rate\":100.0,\"scene\":\"live\",\"sfaceDataList\":[],\"suggestion\":\"pass\"},{\"frames\":[],\"hintWordsInfoList\":[],\"label\":\"normal\",\"logoDataList\":[],\"oCRDataList\":[],\"programCodeDataList\":[],\"rate\":99.9,\"scene\":\"logo\",\"sfaceDataList\":[],\"suggestion\":\"pass\"}]}]},\"requestId\":\"8F6AE3AC-E7E2-4B4A-BB4D-BD4E911260FE\"}\n";
        ScanImageResponse response = JSONObject.parseObject(json, ScanImageResponse.class);
        List<List<ScanImageResponse.Data.Result.SubResult>> subResults = response.getData().getResults().stream().map(result -> result.getSubResults()).collect(Collectors.toList());
        //0:通过 1:疑是 2:不通过
        int resultNumber = 0;
        for (List<ScanImageResponse.Data.Result.SubResult> subResult : subResults) {
            for (ScanImageResponse.Data.Result.SubResult result : subResult) {
                System.out.println("result.getSuggestion():"+result.getSuggestion());
                if("review".equals(result.getSuggestion())){
                    resultNumber = 1;
                    break;
                }
                if("block".equals(result.getSuggestion())){
                    resultNumber = 2;
                    break;
                }
            }
        }
        System.out.println("resultNumber:"+resultNumber);
    }
}