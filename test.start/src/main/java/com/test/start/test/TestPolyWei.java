package com.test.start.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.test.start.test.bean.*;
import com.test.start.test.util.HttpClientUtil;
import com.test.start.test.util.JSONRPC;
import com.test.start.test.util.MD5Util;
import com.test.start.test.util.SHA1Util;
import org.springframework.util.StringUtils;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.*;

public class TestPolyWei {

    private static String userId="aef3afd3d0";
    private static String appId="esacskqwxf";

    private static String secretkey="SoOP5Ph28L";

    private static String appSecretKey="676fb4d7f8704b35a66af31431320e38";

    private static String timestamp=String.valueOf(Calendar.getInstance().getTimeInMillis());

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        //上传本地视频
        //localUpload();
        //上传远程视频
        //sshUpload();
        //获取分类视频信息
        //videos();
        //查询用户所有频道
        //channelsByUserId();
        //获取频道录制视频信息
        //getVideosByChannelId();
        //根据文件Id获取录制视频信息
        //videosByFileId();

        //异步合并直播录制文件
        //videoMerge(); //可以设置结果回调地址 结果为参数请求进来

        //异步批量转存录制文件到点播
        convertLiveVideo(); //可以设置结果回调地址 (记录文件已发送)
        //异步批量转存录制文件到点播回调
        //convertLiveVideoProcess();

        //设置录制回调通知url
        //settingLiveCallBack();
    }

    public static void settingLiveCallBack() {
        Map<String,String> map=new HashMap<>();
        map.put("appId",appId);
        map.put("timestamp",timestamp);
        map.put("url","http://");
        map.put("sign",Ksort(map));

        String url="http://api.polyv.net/live/v2/user/"+userId+"/set-record-callback";
        String doPost = HttpClientUtil.doPost(url, map);
        System.out.println("doPost:"+doPost);
    }

    //异步批量转存录制文件到点播回调
    public static void convertLiveVideoProcess(){
        Map<String,String> map=new HashMap<>();
        map.put("appId",appId);
        map.put("timestamp",timestamp);
        String sign = Ksort(map);
        map.put("sign",sign);
        String url="http://api.polyv.net/live/v2/user/"+userId+"/set-playback-callback";
        String doPost = HttpClientUtil.doPost(url, map);
        System.out.println("doPost:"+doPost);
    }

    //7b1e1f5ea8b5a1df2fa3d3a013eb98db
    //异步批量转存录制文件到点播
    public static void convertLiveVideo(){
        String channelId="397272";
        //a48fa62652d21e21f2c6eac9b30678bb,5bb4fb12d6b8311f59aa49f1fbbeccdb
        //String fileIds="0eed67aee29df3dca22b3ff8562ad164";
        String fileIds="24009f9397fd2cd4257369e7e98ee176,7e8683025a3914d6cf1cff2922361d7a";
        Map<String,String> map=new HashMap<>();
        map.put("appId",appId);
        map.put("timestamp",timestamp);
        map.put("channelId",channelId);
        map.put("fileIds",fileIds);
        //1 2
        map.put("callbackUrl","http://94.191.62.87/xiaoyi/fileForwardSaveCallBack?id=2");
        String sign = Ksort(map);
        map.put("sign",sign);
        String url="http://api.polyv.net/live/v3/channel/record/convert";
        String doPost = HttpClientUtil.doPost(url, map);
        System.out.println("异步批量转存录制文件到点播 doPost:"+doPost);
    }

    //异步合并直播录制文件
    public static void videoMerge(){
        String channelId="383452";
        //a48fa62652d21e21f2c6eac9b30678bb
        String fileIds="8df8f8716e866064f18b6ba25977c934,6f09ba622fa5560b46f8230d2b25cb48,eab2def528425acb61ecf0b2e34dee23,774e091da14e2b7392eec233b0c3c245,e830f1c020f7eb6a24ec617bb3d68297,faaa43dada56fa712fdfdb94c770fb7c,f9290ee2e55564e418d8519fcc460e7e";
        Map<String,String> map=new HashMap<>();
        map.put("appId",appId);
        map.put("timestamp",timestamp);
        map.put("channelId",channelId);
        map.put("fileIds",fileIds);
        map.put("callbackUrl","http://94.191.62.87/api/mergeRecordFileCallBack?id=1");
        map.put("mergeMp4","y");
        map.put("sign",Ksort(map));
        String url="http://api.polyv.net/live/v3/channel/record/merge";
        String json = HttpClientUtil.doPost(url, map);
        System.out.println("doPost:"+json);
        JSONObject object = JSONObject.parseObject(json);
        Object code = object.get("code");
    }

    //根据文件Id获取录制视频信息
    public static void videosByFileId() throws NoSuchAlgorithmException {
        Map<String,String> map=new HashMap<>();
        String channelId="397272";
        String fileId="5bb4fb12d6b8311f59aa49f1fbbeccdb";
        map.put("appId",appId);
        map.put("timestamp",timestamp);
        map.put("channelId",channelId);
        map.put("fileId",fileId);
        String sign = Ksort(map);
        System.out.println("sign:"+sign);
        map.put("sign",sign);
        String url="https://api.polyv.net/live/v3/channel/record/get";
        String json = HttpClientUtil.doGet(url, map);
        System.out.println("json:"+json);
        JSONObject object = JSONObject.parseObject(json);
        Object code = object.get("code");
        QueryTranscribeVideoResponse response=new QueryTranscribeVideoResponse();
        if(!StringUtils.isEmpty(code) && "200".equals(code.toString())){
            response.setCode(object.getString("code"));
            response.setStatus(object.getString("status"));
            response.setMessage(object.getString("message"));
            JSONObject jsonObject = JSONArray.parseObject(object.getString("data"));
            APIRecordFile file=new APIRecordFile();
            file.setDuration(jsonObject.getString("duration"));
            file.setFileName(jsonObject.getString("filename"));
            file.setFileSize(jsonObject.getString("filesize"));
            file.setUserId(jsonObject.getString("userId"));
            file.setFileId(jsonObject.getString("fileId"));
            file.setChannelSessionId(jsonObject.getString("channelSessionId"));
            file.setCreatedTime(jsonObject.getString("createdTime"));
            file.setStartTime(jsonObject.getString("startTime"));
            file.setEndTime(jsonObject.getString("endTime"));
            file.setBitrate(jsonObject.getString("bitrate"));
            file.setResolution(jsonObject.getString("resolution"));
            file.setChannelId(jsonObject.getString("channelId"));
            file.setHeight(jsonObject.getString("height"));
            file.setWidth(jsonObject.getString("width"));
            file.setLiveType(jsonObject.getString("liveType"));
            file.setM3u8(jsonObject.getString("m3u8"));
            file.setMp4(jsonObject.getString("mp4"));
            System.out.println("file:"+file);
        }else{
            //throw new ServiceException(ExceptionConstant.EXCEPTION_JSON_ERROR,"json:"+json);
        }
    }

    //file:5bb4fb12d6b8311f59aa49f1fbbeccdb
    //获取频道录制视频信息
    public static void getVideosByChannelId(){
        Map<String,String> map=new HashMap<>();
        map.put("appId",appId);
        map.put("timestamp",timestamp);
        map.put("userId",userId);
        //map.put("startDate","2019-10-19");
        //map.put("endDate","2019-10-19");
        //map.put("sessionId","fh1l8eit2b");
        String sign = Ksort(map);
        map.put("sign",sign);
        //397272
        String channelId="383452";
        String url="http://api.live.polyv.net/v2/channels/"+channelId+"/recordFiles";
        String json = HttpClientUtil.doGet(url,map);

        System.out.println("json:"+json);
        JSONObject object = JSONObject.parseObject(json);
        Object code = object.get("code");
        QueryTranscribeVideoResponse response=new QueryTranscribeVideoResponse();
        if(!StringUtils.isEmpty(code) && "200".equals(code.toString())){
            response.setCode(object.getString("code"));
            response.setStatus(object.getString("status"));
            response.setMessage(object.getString("message"));
            JSONArray data = JSONArray.parseArray(object.getString("data"));
            List<APIRecordFile> files=new ArrayList<>();
            for (Object datum : data) {
                APIRecordFile file=new APIRecordFile();
                JSONObject jsonObject = JSONObject.parseObject(datum.toString());
                file.setDuration(jsonObject.getString("duration"));
                file.setFileName(jsonObject.getString("fileName"));
                file.setFileSize(jsonObject.getString("fileSize"));
                file.setFileId(jsonObject.getString("fileId"));
                file.setUrl(jsonObject.getString("url"));
                file.setChannelSessionId(jsonObject.getString("channelSessionId"));
                file.setStartTime(jsonObject.getString("startTime"));
                file.setEndTime(jsonObject.getString("endTime"));
                file.setBitrate(jsonObject.getString("bitrate"));
                file.setResolution(jsonObject.getString("resolution"));
                file.setChannelId(jsonObject.getString("channelId"));
                files.add(file);
            }
            System.out.println("files:"+files);
        }else{
            //throw new ServiceException(ExceptionConstant.EXCEPTION_JSON_ERROR,"json:"+json);
        }


    }

    //pd: 397272
    //查询用户所有频道
    public static void channelsByUserId(){
        String appId = "esacskqwxf";
        String userId = "aef3afd3d0";

        long ts = System.currentTimeMillis();
// 创建参数表 （创建接口需要传递的所有参数表）
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("appId", appId);
        paramMap.put("timestamp", Long.toString(ts));

        String sign = Ksort(paramMap);

        String url="http://api.live.polyv.net/v1/users/" + userId + "/channels?appId=" + appId + "&timestamp=" + ts + "&sign=" + sign;
        String json = HttpClientUtil.doGet(url);
        JSONObject object = JSONObject.parseObject(json);
        QueryUserChannelsResponse response=new QueryUserChannelsResponse();
        List<String> data=new ArrayList<>();
        String result = object.getString("result");
        if(!StringUtils.isEmpty(result)){
            JSONArray objects = JSONObject.parseArray(result);
            for (Object o : objects) {
                data.add(o.toString());
            }
            response.setStatus("success");
            response.setData(data);
            System.out.println("response:"+response);
        }
    }

    //保利威 获取分类视频信息
    public static QueryVideoInfoResponse videos() throws NoSuchAlgorithmException {
        QueryVideoInfoResponse response=new QueryVideoInfoResponse();
        String ptime=String.valueOf(Calendar.getInstance().getTimeInMillis());
        String data = "ptime="+ptime+"&userid="+userId+secretkey;
        String sign = SHA1Util.sha1(data).toUpperCase();
        String url="http://api.polyv.net/v2/video/"+userId+"/cataJson?ptime="+ptime+"&sign="+sign+"&cataId=1499328808069";
        String json = HttpClientUtil.doGet(url);
        System.out.println("json:"+json);
        JSONObject object = JSONObject.parseObject(json);
        Object code = object.get("code");
        if(!StringUtils.isEmpty(code) && "200".equals(code.toString())){
            response.setCode(object.getString("code"));
            response.setStatus(object.getString("status"));
            response.setMessage(object.getString("message"));
            String dataJson = object.getString("data");
            Type listType = new TypeToken<List<APINode>>() {}.getType();
            List<APINode> apiNodes = new Gson().fromJson(dataJson, listType);
            response.setData(apiNodes);
        }
        return response;
    }

    public static String Ksort(Map<String, String> paramMap){
        //对参数名进行字典排序
        String[] keyArray = paramMap.keySet().toArray(new String[0]);
        Arrays.sort(keyArray);

//拼接有序的参数串
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(appSecretKey);
        for (String key : keyArray)
        {
            stringBuilder.append(key).append(paramMap.get(key));
        }

        stringBuilder.append(appSecretKey);
        String signSource = stringBuilder.toString();

        return org.apache.commons.codec.digest.DigestUtils.md5Hex(signSource).toUpperCase();
    }

     /*//保利威 上传本地视频
    public static void localUpload() {
        //String writetoken="3e9b0554-1ed1-496f-ab1a-dfe11b792595";
        File file=new File("D:\\WindowsApache\\files\\test.mp4");
        String url="http://v.polyv.net/uc/services/rest?method=uploadfile";
        String doPostFile = HttpClientUtil.uploadFile(url, writetoken, file,JSON.toJSONString(new JSONRPC("标题","标签","描述")));
        System.out.println("doPost:"+doPostFile);
    }

    //保利威 上传远程视频
    public static void sshUpload(){
        //String writetoken="3e9b0554-1ed1-496f-ab1a-dfe11b792595";
        Map<String,String> map=new HashMap<>();
        map.put("writetoken",writetoken);
        map.put("fileUrl","http://94.191.62.87:81/file/test.mp4");
        String url="http://v.polyv.net/uc/services/rest?method=uploadUrlFile";
        String doPost = HttpClientUtil.doPost(url, map);
        System.out.println("doPost:"+doPost);
    }*/



}
