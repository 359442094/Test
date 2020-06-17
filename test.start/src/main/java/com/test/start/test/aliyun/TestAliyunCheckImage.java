package com.test.start.test.aliyun;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.AcsResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.RpcAcsRequest;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.imageaudit.model.v20191230.ScanImageRequest;
import com.aliyuncs.imageaudit.model.v20191230.ScanImageResponse;
import com.aliyuncs.profile.DefaultProfile;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author CJ
 * @date 2020/5/18
 */
public class TestAliyunCheckImage {

    static IAcsClient client = null;

    private static <R extends RpcAcsRequest<T>, T extends AcsResponse> T getAcsResponse(R req) throws Exception {
        try {
            return client.getAcsResponse(req);
        } catch (ServerException e) {
            // 服务端异常
            System.out.println(String.format("ServerException: errCode=%s, errMsg=%s", e.getErrCode(), e.getErrMsg()));
            throw e;
        } catch (ClientException e) {
            // 客户端错误
            System.out.println(String.format("ClientException: errCode=%s, errMsg=%s", e.getErrCode(), e.getErrMsg()));
            throw e;
        } catch (Exception e) {
            System.out.println("Exception:" + e.getMessage());
            throw e;
        }
    }

    public static void printResponse(String actionName, String requestId, AcsResponse  data) {
        System.out.println(String.format("actionName=%s, requestId=%s, data=%s", actionName, requestId,
                JSON.toJSONString(data)));
    }

    /**
     * AccessKey ID
     * LTAIvk4BmnRVVtbK
     *
     * AccessKey Secret
     * LjnnWpVNcbwFjIjVmjRYHumkFpcQQF
     * */
    public static void main(String[] args) throws Exception {

        DefaultProfile profile = DefaultProfile.getProfile(
                "cn-shanghai",             //默认
                "LTAIvk4BmnRVVtbK",         //您的Access Key ID
                "LjnnWpVNcbwFjIjVmjRYHumkFpcQQF");    //您的Access Key Secret

        client = new DefaultAcsClient(profile);

        System.out.println("--------  内容审核 --------------");
        ScanImageRequest req = new ScanImageRequest();
        List<String> scenes = new ArrayList<>();
        //图片智能鉴黄
        scenes.add("porn");
        //图片涉恐涉政识别
        //scenes.add("terrorism");
        //图文违规、鉴黄识别
        //scenes.add("ad");
        //图片不良场景识别
        //scenes.add("live");
        //图片logo识别
        //scenes.add("logo");
        req.setScenes(scenes);
        List<ScanImageRequest.Task> tasks = new ArrayList<>();
        com.aliyuncs.imageaudit.model.v20191230.ScanImageRequest.Task task = new ScanImageRequest.Task();
        task.setDataId(UUID.randomUUID().toString());
        //task.setImageURL("https://viapi-demo.oss-cn-shanghai.aliyuncs.com/viapi-demo/images/ChangeImageSize/change-image-size-src.png");
        //task.setImageURL("http://94.191.62.87:81/file/test2.jpg");
        //task.setImageURL("http://94.191.62.87:81/file/favicon.png");

        task.setImageURL("http://94.191.62.87:81/file/yellow1.png");
        //task.setImageURL("http://94.191.62.87:81/file/yellow2.png");
        //task.setImageURL("http://94.191.62.87:81/file/yellow3.png");

        tasks.add(task);
        req.setTasks(tasks);

        ScanImageResponse resp = getAcsResponse(req);
        System.out.println("resp.getRequestId():"+resp.getRequestId());
        //System.out.println("resp:"+resp);
        printResponse(req.getSysActionName(), resp.getRequestId(), resp);
        List<List<ScanImageResponse.Data.Result.SubResult>> subResults = resp.getData().getResults().stream().map(result -> result.getSubResults()).collect(Collectors.toList());
        boolean flag = true;
        for (List<ScanImageResponse.Data.Result.SubResult> subResult : subResults) {
            for (ScanImageResponse.Data.Result.SubResult result : subResult) {
                if(!"pass".equals(result.getSuggestion())){
                    System.out.println("result.getSuggestion():"+result.getSuggestion());
                    flag = false;
                }
            }
        }
        System.out.println("flag:"+flag);
    }



}
