package com.test.start.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.test.start.test.bean.APICourseOrder;
import com.test.start.test.util.CrawlerUtil;
import com.test.start.test.util.HttpClientUtil;
import com.test.start.test.util.MD5Util;
import com.test.start.test.util.SecurityDesCoder;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

public class TestTYSX {

    private static String suffix="liveCourse!@#$%19";

    private static String interfacePrefix="http://frp.o-learn.cn:51085/thirdparty/";
    //private static String interfacePrefix="http://tysx03.frp.o-learn.cn/thirdparty/";
    //private static String interfacePrefix="https://www.ty-sx.com/thirdparty/";

    public static String EncoderByMd5(String buf) {
        try {
            MessageDigest digist = MessageDigest.getInstance("MD5");
            byte[] rs = digist.digest(buf.getBytes("UTF-8"));
            StringBuffer digestHexStr = new StringBuffer();
            for (int i = 0; i < 16; i++) {
                digestHexStr.append(byteHEX(rs[i]));
            }
            return digestHexStr.toString().toLowerCase();
        } catch (Exception e) {
            //logger.error(e.getMessage(), e);
        }
        return null;

    }

    public static String byteHEX(byte ib) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] ob = new char[2];
        ob[0] = Digit[(ib >>> 4) & 0X0F];
        ob[1] = Digit[ib & 0X0F];
        String s = new String(ob);
        return s;
    }

    public static String getURLEncoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String URLDecoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean isNumericZidai(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    //文档:mobilephone=16621242385&platform=liveCourseConnect&siteName=testSiteName&timestamp=20200108102123&username=4e5de20d7f53502592361fe934931e9e&liveCourse!@#$%19
    //当前:mobilephone=16621242385&platform=liveCourseConnect&siteName=testSiteName&timestamp=20200108102123&username=testUsername&liveCourse!@#$%19
    public static void addTyOrg() throws Exception {
        String siteName="正承教育";
        String username="testName";
        String mobilephone="16621242385";
        String siteDetailNoteOne="机构介绍 1";
        String siteDetailNoteTwo="机构介绍 2";
        String siteDetailNoteThree="机构介绍 3";
        String logoLink="http://94.191.62.87:81/images/1.jpg";
        String imgPcLink="http://94.191.62.87:81/images/1.jpg";
        String imgMobileLink="http://94.191.62.87:81/images/1.jpg";
        String introduce="机构简介";
        String platform="liveCourseConnect";
        String timestamp= org.apache.commons.lang3.time.DateFormatUtils.format(Calendar.getInstance().getTime(), "yyyyMMddHHmmss");
        Map map=new HashMap();
        map.put("siteName",siteName);
        map.put("username",username);
        map.put("mobilephone",mobilephone);
        map.put("timestamp",timestamp);
        map.put("platform",platform);
        String key = Ksort(map);
        map.put("key",key);

        map.put("siteName",getURLEncoderString(siteName));
        map.put("siteDetailNoteOne",getURLEncoderString(siteDetailNoteOne));
        map.put("siteDetailNoteTwo",getURLEncoderString(siteDetailNoteTwo));
        map.put("siteDetailNoteThree",getURLEncoderString(siteDetailNoteThree));
        map.put("introduce",getURLEncoderString(introduce));
        map.put("logoLink",logoLink);
        map.put("imgPcLink",imgPcLink);
        map.put("imgMobileLink",imgMobileLink);

        /*username = new SecurityDesCoder("tyxs9sx").encrypt(username);
        map.put("username",username);*/
        mobilephone = new SecurityDesCoder("tyxs9sx").encrypt(mobilephone);
        map.put("mobilephone",mobilephone);


        String url="http://tysx03.frp.o-learn.cn/thirdparty/siteOrChannelData/addSite";
        String json = HttpClientUtil.doPostContentType(url, map);
        System.out.println(json);

    }

    //文档:channelName=channelName&channelNote=channelNote&platform=platform&timestamp=timestamp&Modsffsafsa
    //当前:channelName=测试&channelNote=channelNote&platform=liveCourseConnect&timestamp=20200108100815&liveCourse!@#$%19
    public static void addTyChannel(){
        String channelName="测试渠道1111";
        String channelNote="渠道备注1111";
        String platform="liveCourseConnect";
        String timestamp= org.apache.commons.lang3.time.DateFormatUtils.format(Calendar.getInstance().getTime(), "yyyyMMddHHmmss");
        Map map=new HashMap();
        map.put("channelName",channelName);
        map.put("channelNote",channelNote);
        map.put("timestamp",timestamp);
        map.put("platform",platform);
        String key = Ksort(map);
        map.put("key",key);
        map.put("channelName",getURLEncoderString(channelName));
        map.put("channelNote",getURLEncoderString(channelNote));
        String url="http://frp.o-learn.cn:51085/thirdparty/siteOrChannelData/addChannel";
        String json = HttpClientUtil.doPost(url,map);
        System.out.println(json);

    }

    //文档:channelName=channelName&channelNum=channelNum&platform=platform&timestamp=timestamp&Modsffsafsa
    //当前:channelName=channelName&channelNum=30&platform=liveCourseConnect&timestamp=20200107173232&liveCourse!@#$%19
    public static void addTyChannelResult(){
        String channelName="正承教育6";
        String channelNo="30";
        String platform="liveCourseConnect";
        String channelNote="备注";
        //渠道状态(0 不通过 1 通过)
        String channelStatus="1";
        String timestamp= org.apache.commons.lang3.time.DateFormatUtils.format(Calendar.getInstance().getTime(), "yyyyMMddHHmmss");

        Map map=new HashMap();
        map.put("channelName",channelName);
        map.put("channelNo",channelNo);
        map.put("timestamp",timestamp);
        map.put("channelNote",channelNote);
        map.put("channelStatus",channelStatus);
        map.put("platform",platform);
        String key = Ksort(map);
        map.put("key",key);

        String url="http://cj.ngrok2.xiaomiqiu.cn/xiaoyi/addTyChannelCallBack";
        String json = HttpClientUtil.doPostContentType(url, map);
        System.out.println(json);

    }

    public static void addTySxUser() throws Exception {
        String functionCode="register";
        String channel="30";
        String id="15211134401";
        String name="张三";
        String platform="liveCourseConnect";
        String timestamp= org.apache.commons.lang3.time.DateFormatUtils.format(Calendar.getInstance().getTime(), "yyyyMMddHHmmss");
        Map<String,String> map=new HashMap<>();
        map.put("functionCode",functionCode);
        map.put("channel",channel);
        map.put("platform",platform);
        map.put("timestamp",timestamp);
        StringBuffer appendStr=new StringBuffer();
        StringBuffer appendPrefix=new StringBuffer("functionCode="+functionCode+"&id="+id);
        if(!StringUtils.isEmpty(name)){
            appendPrefix.append("&name="+ TestTYSX.getURLEncoderString(name));
        }
        String appendSuffix="&platform="+platform+"&channel="+channel+"&timestamp="+timestamp+"&liveCourse!@#$%19";
        appendStr.append(appendPrefix+appendSuffix);
        String key = MD5Util.getMD5(appendStr.toString());
        id = new SecurityDesCoder("tyxs9sx").encrypt(id);
        map.put("id",id);
        if(!StringUtils.isEmpty(name)){
            name = new SecurityDesCoder("tyxs9sx").encrypt(TestTYSX.getURLEncoderString(name));
            map.put("name",name);
        }
        map.put("key",key);
        //注意:thirdParty这里是大写的
        String url = "http://tysx05.frp.o-learn.cn/thirdParty/userRegister/register";
        //String url="https://www.ty-sx.com/thirdParty/userRegister/register";
        System.out.println("添翼申学添加普通用户接口url:"+url);
        String json = HttpClientUtil.doPost(url, map);
        System.out.println("添翼申学添加普通用户接口结果:"+json);
    }

    //测试环境:828081896f5998e4016f693b798902df
    public static void addTeacher() throws Exception {
        String functionCode="addTeacher";
        String userId="15738577721";
        String teacherCertificateNumber="123456";
        String siteName="添翼申学";//"正承教育";
        String orient="暂无";
        String note="暂无";
        String platform="liveCourseConnect";
        String timestamp= DateFormatUtils.format(Calendar.getInstance().getTime(), "yyyyMMddHHmmss");
        Map<String,String> map=new HashMap<>();
        map.put("functionCode",functionCode);
        map.put("note",note);
        map.put("userId",userId);
        map.put("siteName",siteName);
        map.put("orient",orient);
        map.put("platform",platform);
        map.put("timestamp",timestamp);
        String key=Ksort(map);
        userId = new SecurityDesCoder("tyxs9sx").encrypt(userId);
        map.put("userId",userId);
        map.put("teacherCertificateNumber",teacherCertificateNumber);
        map.put("siteName",getURLEncoderString(siteName));
        map.put("orient",getURLEncoderString(orient));
        map.put("note",getURLEncoderString(note));
        map.put("key",key);
        //http://frp.o-learn.cn:51085/hirdparty/
        //String url="https://www.ty-sx.com/thirdparty/liveCourseMaintenance/addTeacher";
        String url=interfacePrefix+"liveCourseMaintenance/addTeacher";
        String post = HttpClientUtil.doPost(url,map);
        System.out.println("post:"+post);
    }

    //修改教师信息:828081896f5998e4016f693b798902df
    public static void editTeacher() throws Exception {
        String functionCode="editTeacher";
        String teacherId="828081896f5998e4016f693b798902df";
        String siteName="添翼申学";
        String name="教师名称";
        String note="教师备注";
        String platform="liveCourseConnect";
        String timestamp= DateFormatUtils.format(Calendar.getInstance().getTime(), "yyyyMMddHHmmss");
        Map<String,String> map=new HashMap<>();
        map.put("functionCode",functionCode);
        map.put("teacherId",teacherId);
        map.put("siteName",siteName);
        map.put("name",name);
        map.put("note",note);
        map.put("platform",platform);
        map.put("timestamp",timestamp);
        String key=Ksort(map);
        map.put("siteName",getURLEncoderString(siteName));
        map.put("name",getURLEncoderString(name));
        map.put("note",getURLEncoderString(note));
        map.put("key",key);
        String url="http://frp.o-learn.cn:51085/thirdparty/liveCourseMaintenance/editTeacher";
        String post = HttpClientUtil.doPost(url,map);
        System.out.println("post:"+post);
    }

    //用户分享
    public static void addUserClass() throws Exception {
        String functionCode="addUserClass";
        String classId="8a888086703d28b801703d4b3769001e";
        String channel="30";
        String mobilephone="16621242385";
        String platform="liveCourseConnect";
        String timestamp= DateFormatUtils.format(Calendar.getInstance().getTime(), "yyyyMMddHHmmss");
        Map<String,String> map=new HashMap<>();
        map.put("functionCode",functionCode);
        map.put("channel",channel);
        map.put("classId",classId);
        map.put("mobilephone",mobilephone);
        map.put("platform",platform);
        map.put("timestamp",timestamp);
        map.put("key",Ksort(map));
        String url= "http://frp.o-learn.cn:51085/thirdparty/liveCourseMaintenance/addUserClass";
        //String url="https://www.ty-sx.com/thirdparty/liveCourseMaintenance/addUserClass";
        String json = HttpClientUtil.doPost(url,map);
        System.out.println(json);
    }
    //删除用户分享
    public static void deleteUserClass() throws Exception {
        String functionCode="deleteUserClass";
        String classId="8a888086703d28b801703d4b3769001e";
        String mobilephone="16621242385";
        String platform="liveCourseConnect";
        String timestamp= DateFormatUtils.format(Calendar.getInstance().getTime(), "yyyyMMddHHmmss");
        Map<String,String> map=new HashMap<>();
        map.put("functionCode",functionCode);
        map.put("classId",classId);
        map.put("mobilephone",mobilephone);
        map.put("platform",platform);
        map.put("timestamp",timestamp);
        map.put("key",Ksort(map));
        String url= "http://frp.o-learn.cn:51085/thirdparty/liveCourseMaintenance/deleteUserClass";
        String json = HttpClientUtil.doPost(url,map);
        System.out.println(json);
    }


    //8a8880866e1a5c23016e1b601986012d
    //添加录播课节
    public static void addRecordedLesson(){
        String functionCode="addRecordLesson";
        //String teacherId="8a8880866e1a5c23016e1b592a790126";
        String classId="ff8080816ef95c83016efcfbde8b1fe4";
        String title="视频标题";
        String code=UUID.randomUUID().toString();
        String coverImgLink="http://94.191.62.87:81/images/1.jpg";
        String vid="aef3afd3d09bbf9671712d08399945ee_a";
        String recordHour="86400000";
        String allowTaste="0";
        String videoDuration="00:00:00";
        String platform="liveCourseConnect";
        String timestamp=DateFormatUtils.format(Calendar.getInstance().getTime(), "yyyyMMddHHmmss");
        Map<String,String> map=new HashMap<>();
        map.put("functionCode",functionCode);
        //map.put("teacherId",teacherId);
        map.put("classId",classId);
        map.put("title",title);
        map.put("code",code);
        map.put("coverImgLink",coverImgLink);

        map.put("vid",vid);
        map.put("recordHour",recordHour);
        map.put("allowTaste",allowTaste);
        map.put("videoDuration",videoDuration);
        map.put("platform",platform);
        map.put("timestamp",timestamp);
        String key=Ksort(map);
        map.put("key",key);
        map.put("title",getURLEncoderString(title));
        //String url="http://frp.o-learn.cn:51085/thirdparty/liveCourseMaintenance/addRecordLesson";
        String url=interfacePrefix+"liveCourseMaintenance/addRecordLesson";
        String post = HttpClientUtil.doPost(url, map);
        System.out.println("post:"+post);
    }

    //8a8880866e1a5c23016e1f663a28016b
    //8a8880866e3ffb5c016e4065e2cb0058
    //ff8080816ef95c83016efcfbde8b1fe4
    //添加录播课程
    public static void addRecordedClass(){
        String functionCode="addRecordClass";
        String name="测试课程1";
        String code= UUID.randomUUID().toString();
        String coverImgLink="http://94.191.62.87:81/images/1.jpg";
        //String coverVid="aef3afd3d0e5dce8c20c66a3d55ed1de_a";

        String enrollStartDate="";
        String enrollEndDate="";
        String expirationDuration="30";
        System.out.println(enrollStartDate);
        System.out.println(enrollEndDate);

        String classHour="10";
        //String totalTime="100";
        String classType="素质教育";  //学科教育 素质教育 国际教育
        String recordType="1";
        String primeCost="666";
        String cost="666";
        String siteName="添翼申学";
        //String siteName="正承教育";
        String platform="liveCourseConnect";
        String timestamp= DateFormatUtils.format(Calendar.getInstance().getTime(), "yyyyMMddHHmmss");
        Map<String,String> map=new HashMap<>();
        String maxNum="99999";
        map.put("expirationDuration",expirationDuration);
        //map.put("coverVid",coverVid);
        map.put("maxNum",maxNum);
        map.put("siteName",siteName);
        map.put("functionCode",functionCode);
        map.put("name",name);
        map.put("code",code);
        map.put("coverImgLink",coverImgLink);
        if(StringUtils.isEmpty(enrollStartDate)){
            Calendar instance = Calendar.getInstance();
            enrollStartDate= org.apache.commons.lang3.time.DateFormatUtils.format(instance.getTime(), "yyyyMMddHHmmss");
        }
        if(StringUtils.isEmpty(enrollEndDate)){
            Calendar instance = Calendar.getInstance();
            instance.add(Calendar.DATE,7);
            enrollEndDate= org.apache.commons.lang3.time.DateFormatUtils.format(instance.getTime(), "yyyyMMddHHmmss");
        }
        map.put("enrollStartDate",enrollStartDate);
        map.put("enrollEndDate",enrollEndDate);
        map.put("classHour",classHour);
        //map.put("totalTime",totalTime);
        map.put("classType",classType);
        map.put("recordType",recordType);
        map.put("primeCost",primeCost);
        map.put("cost",cost);
        map.put("platform",platform);
        map.put("timestamp",timestamp);
        String key=Ksort(map);
        map.put("key",key);


        map.put("siteName",getURLEncoderString(siteName));
        map.put("name",getURLEncoderString(name));
        map.put("classType",getURLEncoderString(classType));
        if(!"2".equals(recordType)){
            String courseIntroduceImg="课程主图";
            String courseInformation="课程简介";
            String courseTeachersHighlight="师资介绍";
            String courseHighlight="课程亮点";
            String courseLearningContent="学习内容";
            String courseObservationStyle="观看方式";
            String courseConsultant="课程咨询";
            String courseWarmPrompt="温馨提示";
            map.put("courseIntroduceImg",getURLEncoderString(courseIntroduceImg));
            map.put("courseInformation",getURLEncoderString(courseInformation));
            map.put("courseTeachersHighlight",getURLEncoderString(courseTeachersHighlight));
            map.put("courseHighlight",getURLEncoderString(courseHighlight));
            map.put("courseLearningContent",getURLEncoderString(courseLearningContent));
            map.put("courseObservationStyle",getURLEncoderString(courseObservationStyle));
            map.put("courseConsultant",getURLEncoderString(courseConsultant));
            map.put("courseWarmPrompt",getURLEncoderString(courseWarmPrompt));

        }

        //String url="http://frp.o-learn.cn:51085/thirdparty/liveCourseMaintenance/addRecordClass";
        String url=interfacePrefix+"liveCourseMaintenance/addRecordClass";
        String post = HttpClientUtil.doPost(url,map);
        System.out.println("post:"+post);
    }

    //课程订单查询
    public static void getClassOrder(){
        String functionCode="getClassOrder";
        //ff8080816cb31cc8016cb6288bfd2bc2
        //ff8080816c759c5b016c785c402b11e0
        String classId="ff8080816c759c5b016c785c402b11e0";
        String platform="liveCourseConnect";
        String timestamp=DateFormatUtils.format(Calendar.getInstance().getTime(),"yyyyMMddHHmmss");
        Map<String,String> map=new HashMap<>();
        map.put("functionCode",functionCode);
        map.put("classId",classId);
        map.put("platform",platform);
        map.put("timestamp",timestamp);
        String key=Ksort(map);
        map.put("key",key);
        String url=interfacePrefix+"liveCourseMaintenance/getClassOrder";
        String post = HttpClientUtil.doPost(url, map);
        System.out.println("post:"+post);
        JSONObject jsonObject = JSONObject.parseObject(post);
        JSONArray returnData = JSON.parseArray(jsonObject.getString("returnData"));
        List<APICourseOrder> orders=new ArrayList<>();
        for (Object returnDatum : returnData) {
            JSONObject object = JSONObject.parseObject(returnDatum.toString());
            APICourseOrder order=new APICourseOrder();
            order.setSiteName(object.getString("siteName"));
            order.setOrderNum(object.getString("orderNum"));
            order.setPayStatus(object.getString("payStatus"));
            order.setPayType(object.getString("payType"));
            order.setClassCost(new BigDecimal(object.getString("classCost")));
            order.setTotalCost(new BigDecimal(object.getString("totalCost")));
            order.setUserChannel(object.getString("userChannel"));
            order.setUserMobile(object.getString("userMobile"));
            order.setUserName(object.getString("userName"));
            order.setOrderFinishDate(object.getDate("orderFinishDate"));
            order.setOrderExpireDate(object.getDate("orderExpireDate"));
            order.setRefundName(object.getString("refundName"));
            order.setRefundDate(object.getDate("refundDate"));
            orders.add(order);
        }
        System.out.println("orders:"+orders);
    }

    //课程情况查询
    public static void getClassDetail(){
        String functionCode="getClassDetail";
        String classId="ff8080816dba269d016dbdeb10fd5878";
        String platform="liveCourseConnect";
        String timestamp=DateFormatUtils.format(Calendar.getInstance().getTime(), "yyyyMMddHHmmss");
        ;
        Map<String,String> map=new HashMap<>();
        map.put("functionCode",functionCode);
        map.put("classId",classId);
        map.put("platform",platform);
        map.put("timestamp",timestamp);

        String key = Ksort(map);
        map.put("key",key);
        map.put("platform",getURLEncoderString(platform));
        String url=interfacePrefix+"liveCourseMaintenance/getClassDetail";
        String post = HttpClientUtil.doPost(url, map);
        System.out.println("post:"+post);
        JSONObject returnData = JSONObject.parseObject(post);
        JSONObject returnData1 = JSON.parseObject(returnData.get("returnData").toString());
        JSONArray jsonObject = JSONObject.parseArray(returnData1.get("lessonDatas").toString());
        for (int i = 0; i < jsonObject.size(); i++) {
            JSONObject jsonObject1 = JSONObject.parseObject(jsonObject.get(i).toString());
            System.out.println("liveContent:"+jsonObject1.get("liveContent"));
        }
    }

    //直播课节添加  8a888086703c523501703c84527a003a
    public static void addLesson(){
        String functionCode="addLesson";
        String teacherId="828081896f5998e4016f693b798902df";
        String classId="8a8880867037900c01703c6718a303d7";
        String liveContent="直播内容";
        String code=UUID.randomUUID().toString();

        String liveStartDate="20200227000000";
        String liveEndDate="20200228000000";
        String lessonHour="86400000";
        String liveManNumber="15";
        String platform="liveCourseConnect";
        String timestamp=DateFormatUtils.format(Calendar.getInstance().getTime(), "yyyyMMddHHmmss");
        ;
        Map<String,String> map=new HashMap<>();
        map.put("functionCode",functionCode);
        map.put("teacherId",teacherId);
        map.put("classId",classId);
        map.put("liveContent",liveContent);
        map.put("code",code);
        map.put("liveStartDate",liveStartDate);
        map.put("liveEndDate",liveEndDate);
        map.put("lessonHour",lessonHour);
        map.put("liveManNumber",liveManNumber);
        map.put("platform",platform);
        map.put("timestamp",timestamp);
        String key=Ksort(map);
        map.put("key",key);
        map.put("liveContent",getURLEncoderString(liveContent));
        String url=interfacePrefix+"liveCourseMaintenance/addLesson";
        String post = HttpClientUtil.doPost(url, map);
        System.out.println("post:"+post);
    }

    //直播课节修改 ff8080816dba26a7016dbdf418501ae5
    public static void editLiveLesson(){
        String functionCode="editLesson";
        String teacherId="828081896f5998e4016f693b798902df";
        String classId="8a8880867037900c01703c6718a303d7";
        String lessonId="8a888086705762280170576494de003c";
        String liveContent="直播内容/课节标题";
        String liveStartDate="20200227000000";
        String liveEndDate="20200228000000";
        String platform="liveCourseConnect";
        String timestamp=DateFormatUtils.format(Calendar.getInstance().getTime(), "yyyyMMddHHmmss");
        ;
        Map<String,String> map=new HashMap<>();
        map.put("functionCode",functionCode);
        map.put("teacherId",teacherId);
        map.put("classId",classId);
        map.put("lessonId",lessonId);
        map.put("liveContent",liveContent);
        map.put("liveStartDate",liveStartDate);
        map.put("liveEndDate",liveEndDate);
        map.put("platform",platform);
        map.put("timestamp",timestamp);
        String key=Ksort(map);
        map.put("key",key);
        map.put("liveContent",getURLEncoderString(liveContent));
        String url="http://frp.o-learn.cn:51085/thirdparty/liveCourseMaintenance/editLesson";
        String post = HttpClientUtil.doPost(url, map);
        System.out.println("post:"+post);
    }

    //直播课节删除 ff8080816dba26a7016dbdf418501ae5
    public static void deleteLiveLesson(){
        String functionCode="deleteLesson";
        String classId="8a8880867037900c01703c6718a303d7";
        String lessonId="8a888086703d28b801703d2a797e000b";
        String platform="liveCourseConnect";
        String timestamp=DateFormatUtils.format(Calendar.getInstance().getTime(), "yyyyMMddHHmmss");
        ;
        Map<String,String> map=new HashMap<>();
        map.put("functionCode",functionCode);
        map.put("classId",classId);
        map.put("lessonId",lessonId);
        map.put("platform",platform);
        map.put("timestamp",timestamp);
        String key=Ksort(map);
        map.put("key",key);
        String url="http://frp.o-learn.cn:51085/thirdparty/liveCourseMaintenance/deleteLesson";
        String post = HttpClientUtil.doPost(url, map);
        System.out.println("post:"+post);
    }


    //测试:828081876fa81d07016fabdef39a105b
    //正式:ff8080816fa55ebf016fabe694775728
    public static void addLiveClass() throws Exception {
        String functionCode="addLiveCourse";
        String name="测试课程1";
        String code= UUID.randomUUID().toString();
        String coverImgLink="http://94.191.62.87:81/images/1.jpg";
        String enrollStartDate="20200213080000";/*DateFormatUtils.format(Calendar.getInstance().getTime(), "yyyyMMddHHmmss");
        ;*/
        String enrollEndDate="20200214080000";/*DateFormatUtils.format(Calendar.getInstance().getTime(), "yyyyMMddHHmmss");
        ;*/
        String classHour="10";
        String classType="素质教育"; //学科教育 素质教育 国际教育
        String primeCost="666";
        String cost="666";
        //String siteName="正承教育";
        String siteName="添翼申学";
        String platform="liveCourseConnect";
        String timestamp= DateFormatUtils.format(Calendar.getInstance().getTime(), "yyyyMMddHHmmss");
        Map<String,String> map=new HashMap<>();
        String maxNum="100";
        map.put("expirationDuration","366");
        map.put("showEvaluation","1");
        map.put("maxNum",maxNum);
        map.put("siteName",siteName);
        map.put("functionCode",functionCode);
        map.put("name",name);
        map.put("code",code);
        map.put("coverImgLink",coverImgLink);
        map.put("enrollStartDate",enrollStartDate);
        map.put("enrollEndDate",enrollEndDate);
        map.put("classHour",classHour);
        map.put("classType",classType);
        map.put("primeCost",primeCost);
        map.put("cost",cost);
        map.put("platform",platform);
        map.put("timestamp",timestamp);

        String key=Ksort(map);
        map.put("key",key);

        String courseIntroduceImg="课程主图";
        String courseInformation="课程简介";
        String courseTeachersHighlight="师资介绍";
        String courseHighlight="课程亮点";
        String courseLearningContent="学习内容";
        String courseObservationStyle="观看方式";
        String courseConsultant="课程咨询";
        String courseWarmPrompt="温馨提示";
        map.put("siteName",getURLEncoderString(siteName));
        map.put("name",getURLEncoderString(name));
        map.put("classType",getURLEncoderString(classType));
        map.put("courseIntroduceImg",courseIntroduceImg);
        map.put("courseInformation",courseInformation);
        map.put("courseTeachersHighlight",courseTeachersHighlight);
        map.put("courseHighlight",courseHighlight);
        map.put("courseLearningContent",courseLearningContent);
        map.put("courseObservationStyle",courseObservationStyle);
        map.put("courseConsultant",courseConsultant);
        map.put("courseWarmPrompt",courseWarmPrompt);
        /*map.put("courseIntroduceImg",getURLEncoderString(courseIntroduceImg));
        map.put("courseInformation",getURLEncoderString(courseInformation));
        map.put("courseTeachersHighlight",getURLEncoderString(courseTeachersHighlight));
        map.put("courseHighlight",getURLEncoderString(courseHighlight));
        map.put("courseLearningContent",getURLEncoderString(courseLearningContent));
        map.put("courseObservationStyle",getURLEncoderString(courseObservationStyle));
        map.put("courseConsultant",getURLEncoderString(courseConsultant));
        map.put("courseWarmPrompt",getURLEncoderString(courseWarmPrompt));*/
        String url=interfacePrefix+"liveCourseMaintenance/addLiveClass";
        //String url="https://www.ty-sx.com/thirdparty/liveCourseMaintenance/addLiveClass";
        String post = HttpClientUtil.doPost(url,map);
        System.out.println("post:"+post);
    }
    //测试:8a8880867037900c01703c6718a303d7
    //正式:ff8080816fa55ebf016fabe694775728
    public static void editLiveClass() throws Exception {
        String functionCode="editLiveClass";
        String classId="8a8880867037900c01703c6718a303d7";
        String siteName="添翼申学";
        //String siteName="添翼申学";
        String name = "测试修改直播课程2";
        String coverImgLink="http://94.191.62.87:81/images/1.jpg";
        String enrollStartDate=DateFormatUtils.format(Calendar.getInstance().getTime(), "yyyyMMddHHmmss");
        ;
        String enrollEndDate=DateFormatUtils.format(Calendar.getInstance().getTime(), "yyyyMMddHHmmss");
        ;
        String maxNum="101";
        String primeCost="555";
        String cost="555";
        String platform="liveCourseConnect";
        String timestamp= DateFormatUtils.format(Calendar.getInstance().getTime(), "yyyyMMddHHmmss");
        Map<String,String> map=new HashMap<>();
        map.put("functionCode",functionCode);
        map.put("classId",classId);
        map.put("siteName",siteName);
        map.put("name",name);
        map.put("coverImgLink",coverImgLink);
        map.put("enrollStartDate",enrollStartDate);
        map.put("enrollEndDate",enrollEndDate);
        map.put("maxNum",maxNum);
        map.put("primeCost",primeCost);
        map.put("cost",cost);
        map.put("platform",platform);
        map.put("timestamp",timestamp);

        String key=Ksort(map);
        map.put("key",key);

        map.put("siteName",getURLEncoderString(siteName));
        map.put("name",getURLEncoderString(name));
        //String url=interfacePrefix+"liveCourseMaintenance/addLiveClass";
        String url="http://frp.o-learn.cn:51085/thirdparty/liveCourseMaintenance/editLiveClass";
        String post = HttpClientUtil.doPost(url,map);
        System.out.println("post:"+post);
    }


    public static String Ksort(Map<String, String> map){
        String str = "";
        String[] key = new String[map.size()];
        int index = 0;
        for (String k : map.keySet()) {
            key[index] = k;
            index++;
        }
        Arrays.sort(key);
        for (String s : key) {
            str += s + "=" + map.get(s) + "&";
        }
        String appentStr= str +suffix;
        System.out.println("appentStr:"+appentStr);
        return MD5Util.getMD5(appentStr);
    }
}
