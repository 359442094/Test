package com.test.start.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.test.common.util.MD5HexUtil;
import com.test.start.test.bean.*;
import com.test.start.test.util.HttpClientUtil;
import com.test.start.test.util.JSONRPC;
import com.test.start.test.util.MD5Util;
import com.test.start.test.util.SHA1Util;
import org.bouncycastle.util.encoders.UrlBase64;
import org.springframework.util.StringUtils;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
        //convertLiveVideo(); //可以设置结果回调地址 (记录文件已发送)
        //异步批量转存录制文件到点播回调
        //convertLiveVideoProcess();

        //设置录制回调通知url
        //settingLiveCallBack();

        //根据直播频道及场次和视频ID查询直播转存视频相关信息
        //getVideosByVid();

        //导出合并的录制文件并回调mp4下载地址
        //exportVideoFile();

        //获取播放视频token
        //getPolyVideoToken();

        //恢复频道号推流
        //recoverPistonFlow();
        //停止频道号推流
        //stopPistonFlow();

        //添加视频点播分类
        //addVideoClass();
        //添加直播频道
        //addLiveChannel();

       getVideoOneInfo();

    }

    //获取单个视频信息
    public static void getVideoOneInfo() throws NoSuchAlgorithmException {
        String vid="aef3afd3d04e86cc3890286ebb834487_a";
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        String ptime = String.valueOf(instance.getTimeInMillis());
        String data = "ptime="+ptime+"&vid="+vid+secretkey;
        String sign = SHA1Util.sha1(data).toUpperCase();
        Map<String,String> map=new HashMap<>();
        map.put("ptime",ptime);
        map.put("vid",vid);
        map.put("sign",sign);
        String url="http://api.polyv.net/v2/video/"+userId+"/get-video-msg";//?ptime="+ptime+"&sign="+sign;//?ptime="+ptime+"&sign="+sign+"&cataId=1499328808069";
        String json = HttpClientUtil.doPost(url,map);
        //System.out.println("json:"+json);
        JSONObject jsonObject = JSONObject.parseObject(json);
        JSONArray jsonArray = JSONObject.parseArray(jsonObject.getString("data"));
        for (Object o : jsonArray) {
            JSONObject jsonObject1 = JSONObject.parseObject(o.toString());
            //APIVideoOneInfo apiVideoOneInfo = JSONObject.parseObject(o.toString(), APIVideoOneInfo.class);
            System.out.println("jsonObject1 = " + jsonObject1.getString("status"));
        }

        //System.out.println("jsonArray = " + jsonArray);
        /*System.out.println("videoOneInfo = " + jsonObject1.getString("status"));*/
    }

    //添加直播频道
    public static void addLiveChannel() {
        String name = "直播频道名称";
        Map<String,String> map=new HashMap<>();
        String ptime = String.valueOf(Calendar.getInstance().getTimeInMillis());
        map.put("appId",appId);
        map.put("timestamp",ptime);
        map.put("userId",userId);
        map.put("name",name);
        map.put("channelPasswd","123456");
        String ksort = Ksort(map);
        map.put("sign",ksort);
        String url = "http://api.polyv.net/live/v2/channels/";
        String post = HttpClientUtil.doPost(url, map);
        System.out.println("post = " + post);
        JSONObject jsonObject = JSONObject.parseObject(post);
        String code = jsonObject.getString("code");
        String status = jsonObject.getString("status");
        String message = jsonObject.getString("message");
        APILiveChannel channel = JSONObject.parseObject(jsonObject.getString("data"), APILiveChannel.class);
        channel.setCode(code);
        channel.setStatus(status);
        channel.setMessage(message);
        System.out.println(channel);
    }


    //添加视频点播分类(不能同名)
    public static void addVideoClass() throws NoSuchAlgorithmException {
        Map<String,String> map=new HashMap<>();
        String cataName="11111";
        //视频分类上级目录 默认:正承教育
        String parentId="1575345411954";
        map.put("cataname",cataName);
        map.put("parentid",parentId);
        map.put("ptime",timestamp);
        String data ="cataname="+cataName+"&parentid="+parentId+"&ptime="+timestamp+secretkey;
        String sign = SHA1Util.sha1(data).toUpperCase();
        map.put("sign",sign);
        String url="http://api.polyv.net/v2/video/"+userId+"/addCata";
        String json = HttpClientUtil.doPost(url, map);
        System.out.println("json = " + json);
        JSONObject jsonObject = JSONObject.parseObject(json);
        String code = jsonObject.getString("code");
        String status = jsonObject.getString("status");
        String message = jsonObject.getString("message");
        if("200".equals(code)){
            APICata cata = JSONObject.parseObject(jsonObject.getString("data"), APICata.class);
            cata.setCode(code);
            cata.setStatus(status);
            cata.setMessage(message);
            System.out.println("cata:"+cata);
        }
    }

    //停止频道号推流
    public static void stopPistonFlow() {
        String channelId = "383452";
        Map<String,String> map=new HashMap<>();
        String ptime = String.valueOf(Calendar.getInstance().getTimeInMillis());
        map.put("appId",appId);
        map.put("timestamp",ptime);
        map.put("userId",userId);
        String ksort = Ksort(map);
        map.put("sign",ksort);
        String url = "http://api.polyv.net/live/v2/stream/"+channelId+"/cutoff";
        String post = HttpClientUtil.doPost(url, map);
        System.out.println("post = " + post);
        JSONObject jsonObject = JSONObject.parseObject(post);
        System.out.println(jsonObject.getString("code"));
        System.out.println(jsonObject.getString("status"));
        System.out.println(jsonObject.getString("message"));
        System.out.println(jsonObject.getString("data"));
    }

    //恢复频道号推流
    public static void recoverPistonFlow() {
        String channelId = "383452";
        Map<String,String> map=new HashMap<>();
        String ptime = String.valueOf(Calendar.getInstance().getTimeInMillis());
        map.put("appId",appId);
        map.put("timestamp",ptime);
        map.put("userId",userId);
        String ksort = Ksort(map);
        map.put("sign",ksort);
        String url = "http://api.polyv.net/live/v2/stream/"+channelId+"/resume";
        String post = HttpClientUtil.doPost(url, map);
        System.out.println("post = " + post);

    }

    //获取播放视频token
    public static void getPolyVideoToken() throws NoSuchAlgorithmException {
        String viewerId = UUID.randomUUID().toString();
        String vid="aef3afd3d04e86cc3890286ebb834487_a";
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        String ptime = String.valueOf(instance.getTimeInMillis());
        String data = secretkey+"ts"+ptime+"userId"+userId+"videoId"+vid+"viewerId"+viewerId+secretkey;
        String sign = MD5Util.getMD5(data).toUpperCase();
        Map<String,String> map=new HashMap<>();
        map.put("userId",userId);
        map.put("ts",ptime);
        map.put("videoId",vid);
        map.put("viewerId",viewerId);
        map.put("sign",sign);
        String url="https://hls.videocc.net/service/v1/token";
        String json = HttpClientUtil.doPost(url,map);
        System.out.println("json:"+json);
        JSONObject jsonObject = JSONObject.parseObject(json);
        System.out.println(jsonObject.getInteger("code"));
        System.out.println(jsonObject.getString("status"));
        System.out.println(jsonObject.getString("message"));

        JSONObject data1 = JSONObject.parseObject(jsonObject.get("data").toString());
        System.out.println(StringUtils.isEmpty(data1.getString("token"))?"":data1.getString("token"));
        System.out.println(data1.getString("userId"));
        System.out.println(data1.getString("appId"));
        System.out.println(data1.getString("videoId"));
        System.out.println(data1.getString("viewerIp"));
        System.out.println(data1.getString("viewerId"));
        System.out.println(data1.getString("viewerName"));
        System.out.println(data1.getString("extraParams"));
        System.out.println(data1.getString("ttl"));
        System.out.println(data1.getLong("createdTime"));
        System.out.println(data1.getLong("expiredTime"));
        System.out.println(data1.getString("iswxa"));
        System.out.println(data1.getBoolean("disposable"));
    }


    //导出合并的录制文件并回调mp4下载地址
    public static void exportVideoFile() {

        Map<String,String> map=new HashMap<>();
        String ptime = String.valueOf(Calendar.getInstance().getTimeInMillis());
        map.put("appId",appId);
        map.put("timestamp",ptime);
        map.put("channelId","383452");
        map.put("startTime","20191111090843");
        map.put("endTime","20191111103844");
        String ksort = Ksort(map);
        map.put("sign",ksort);
        String url="http://api.polyv.net/live/v3/channel/record/merge-mp4";
        String post = HttpClientUtil.doPost(url, map);
        System.out.println("post = " + post);

    }

    //根据直播频道及场次和视频ID查询直播转存视频相关信息
    public static void getVideosByVid() throws NoSuchAlgorithmException {
        String vid="aef3afd3d04e86cc3890286ebb834487_a";
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        String valueOf = String.valueOf(instance.getTimeInMillis());
        String ptime= valueOf;
        String data = "ptime="+ptime+"&vid="+vid+secretkey;
        String sign = SHA1Util.sha1(data).toUpperCase();
        Map<String,String> map=new HashMap<>();
        map.put("ptime",ptime);
        //map.put("userId",userId);
        map.put("vid",vid);
        map.put("sign",sign);
        String url="http://api.polyv.net/v2/video/"+userId+"/get-live-playback";//?ptime="+ptime+"&sign="+sign;//?ptime="+ptime+"&sign="+sign+"&cataId=1499328808069";
        String json = HttpClientUtil.doPost(url,map);
        System.out.println("json:"+json);
        JSONObject jsonObject=JSONObject.parseObject(json);
        System.out.println("code:"+jsonObject.getString("code"));
        System.out.println("status = " + jsonObject.getString("status"));
        System.out.println("message = " + jsonObject.getString("message"));
        JSONArray jsonArray = JSON.parseArray(jsonObject.getString("data"));
        for (Object result : jsonArray) {
            JSONObject resultObject = JSONObject.parseObject(result.toString());
            System.out.println("data = " + result);
            System.out.println("vid = " + resultObject.getString("vid"));
            System.out.println("fileSize = " + resultObject.getInteger("fileSize"));
            System.out.println("fileUrl = " + resultObject.getString("fileUrl"));
            System.out.println("sessionId = " + resultObject.getString("sessionId"));
            System.out.println("type = " + resultObject.getString("type"));
            System.out.println("channelId = " + resultObject.getString("channelId"));
        }
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
        String channelId="383452";
        String fileIds="a122c61782130a40cc1b83b60e5cf0ab,dedff516785d5883f7647b5628c846c7";
        Map<String,String> map=new HashMap<>();
        map.put("appId",appId);
        map.put("timestamp",timestamp);
        map.put("channelId",channelId);
        map.put("fileIds",fileIds);
        map.put("cataId","1575345411954");
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
        String fileIds="eafc9c2e6acd7a13d2ad5045ac0c319a,f4544da5da872494fa283061ef0cd176";
        Map<String,String> map=new HashMap<>();
        map.put("appId","esacskqwxf");
        map.put("timestamp",String.valueOf(Calendar.getInstance().getTimeInMillis()));
        map.put("channelId",channelId);
        map.put("fileIds",fileIds);
        map.put("callbackUrl","http://94.191.62.87/xiaoyi/mergeRecordFileCallBack?fileIds="+fileIds+"&fileId=2&fileUrl=3&fileName=4&id=5");
        map.put("mergeMp4","Y");
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
        String fileId="0eed67aee29df3dca22b3ff8562ad164";
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
        String url="http://api.polyv.net/v2/video/"+userId+"/cataJson?ptime="+ptime+"&cataid=1568709388039"+"&sign="+sign;
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

    public static String KsortVid(Map<String, String> paramMap){
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

        stringBuilder.append(secretkey);
        String signSource = stringBuilder.toString();
        return signSource;
        //return org.apache.commons.codec.digest.DigestUtils.md5Hex(signSource).toUpperCase();
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
