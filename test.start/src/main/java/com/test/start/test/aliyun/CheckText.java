package com.test.start.test.aliyun;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.AcsResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.RpcAcsRequest;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.imageaudit.model.v20191230.ScanTextRequest;
import com.aliyuncs.imageaudit.model.v20191230.ScanTextResponse;
import com.aliyuncs.profile.DefaultProfile;
import java.util.ArrayList;
import java.util.List;

/**
 * @author CJ
 * @date 2020/5/27
 */
public class CheckText {

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
        ScanTextRequest req = new ScanTextRequest();
        List<ScanTextRequest.Labels> labelss=new ArrayList<>();
        ScanTextRequest.Labels labels=new ScanTextRequest.Labels();
        labels.setLabel("abuse");
        labelss.add(labels);
        req.setLabelss(labelss);
        List<ScanTextRequest.Tasks> tasks = new ArrayList<>();
        com.aliyuncs.imageaudit.model.v20191230.ScanTextRequest.Tasks task = new ScanTextRequest.Tasks();
        task.setContent("某是傻逼");
        tasks.add(task);
        req.setTaskss(tasks);

        ScanTextResponse resp = getAcsResponse(req);
        System.out.println("resp:"+resp);
        ScanTextResponse.Data data = resp.getData();
        List<ScanTextResponse.Data.Element> elements = data.getElements();

        int resultNumber=0;
        for (ScanTextResponse.Data.Element element : elements) {
            List<ScanTextResponse.Data.Element.Result> results = element.getResults();
            for (ScanTextResponse.Data.Element.Result result : results) {
                String suggestion = result.getSuggestion();
                System.out.println("suggestion："+suggestion);
                if("review".equals(suggestion)){
                    resultNumber = 1;
                    System.out.println("检测结果不确定:"+result.getSuggestion());
                    break;
                }else if("block".equals(result.getSuggestion())){
                    resultNumber = 2;
                    System.out.println("文本违规:"+result.getSuggestion());
                    break;
                }else{
                    resultNumber = 0;
                    System.out.println("文本正常:"+result.getSuggestion());
                    break;
                }
            }
        }

        System.out.println("resultNumber:"+resultNumber);

    }

}
