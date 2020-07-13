package com.test.start.test.crawler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.util.NameValuePair;
import com.test.start.test.util.CrawlerUtil;
import org.springframework.util.StringUtils;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author CJ
 * @date 2020/7/10
 */
public class Test {

    public static void main(String[] args) throws IOException {
        /*根据课节编号获取课节的加密编号*/
        String url = "https://www.eeo.cn/saasajax/webcast.ajax.php?action=getLessonList";

        URL link = new URL(url);
        String refer = "https://www.eeo.cn/webcast.php?courseKey=7a30062bb9b028f9&lessonid=189128012";
        WebRequest request = new WebRequest(link,HttpMethod.POST);
        request.setCharset("UTF-8");
        //设置请求报文头里的refer字段
        request.setAdditionalHeader("cookie", "Hm_lvt_f1d3b48491a1ba98688c81e330d19117=1594349755; PHPSESSID=gmb1eeoj550s5e3ves55r76pa0; Hm_lpvt_f1d3b48491a1ba98688c81e330d19117=1594353152");
        request.setAdditionalHeader("origin","https://www.eeo.cn");
        request.setAdditionalHeader("Referer", refer);
        //设置请求报文头里的User-Agent字段
        request.setAdditionalHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");

        List<NameValuePair> requestParameters=new ArrayList<>();
        requestParameters.add(new NameValuePair("courseKey","7a30062bb9b028f9"));
        request.setRequestParameters(requestParameters);

        //获取指定路径页面
        String contentJson = CrawlerUtil.startReptile(request);

        System.out.println("contentJson:"+contentJson);

        JSONObject object = JSONObject.parseObject(contentJson);
        if(object!=null && !StringUtils.isEmpty(object.getString("data"))){
            String data = object.getString("data");
            JSONArray objects = JSONObject.parseArray(data);
            for (Object o : objects) {
                JSONObject lessonData = JSONObject.parseObject(o.toString());
                Object lessonId = lessonData.get("lessonId");
                if("189128012".equals(lessonId)){
                    Object lessonKey = lessonData.get("lessonKey");
                    System.out.println("lessonKey:"+lessonKey);
                }
            }
        }

        /*获取课节的classIn观看人数*/
        /*String url = "https://www.eeo.cn/saasajax/webcast.ajax.php?action=getClassCastInfo";

        URL link = new URL(url);
        String refer = "https://www.eeo.cn/live.php?lessonKey=8b98c51c2ff1d1fb";
        WebRequest request = new WebRequest(link,HttpMethod.POST);
        request.setCharset("UTF-8");
        //设置请求报文头里的refer字段
        request.setAdditionalHeader("cookie", "PHPSESSID=oeou95naft5elaalupenis21g7; Hm_lvt_f1d3b48491a1ba98688c81e330d19117=1594349755; Hm_lpvt_f1d3b48491a1ba98688c81e330d19117=1594350020");
        request.setAdditionalHeader("origin","https://www.eeo.cn");
        request.setAdditionalHeader("Referer", refer);
        //设置请求报文头里的User-Agent字段
        request.setAdditionalHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");

        List<NameValuePair> requestParameters=new ArrayList<>();
        requestParameters.add(new NameValuePair("lessonKey","8b98c51c2ff1d1fb"));
        //requestParameters.add(new NameValuePair("account","yk_47mKZH6iK111"));
        request.setRequestParameters(requestParameters);

        //获取指定路径页面
        String contentJson = CrawlerUtil.startReptile(request);
        //String contentJson = "{\"error_info\":{\"errno\":1,\"error\":\"\\u7a0b\\u5e8f\\u6b63\\u5e38\\u6267\\u884c\"},\"data\":{\"playNum\":16,\"likeNum\":0,\"likeStatus\":0,\"onlineNum\":1,\"noteContent\":\"\",\"updateTime\":\"\",\"goodsList\":[]}}\n";
        System.out.println("contentJson:"+contentJson);

        JSONObject object = JSONObject.parseObject(contentJson);
        if(object!=null && !StringUtils.isEmpty(object.getString("data"))){
            String data = object.getString("data");
            JSONObject object1 = JSONObject.parseObject(data);
            System.out.println("playNum:"+object1.getInteger("playNum"));
        }*/
    }
}
