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

    private static String prefix="liveCourse!@#$%19";

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

    public static void main(String[] args) throws Exception {
        //addTeacher();
        //addLiveClass();
        //addLesson();
        //getClassDetail();
        //getClassOrder();
        //addRecordedClass();
        //addRecordedLesson();
        //addUserClass();
        addTySxUser();
    }

    public static void addTySxUser() {
        String functionCode="register";
        String channel="428544";
        String id="16621242384";
        String platform="liveCourseConnect";
        String timestamp=DateFormatUtils.format(Calendar.getInstance().getTime(), "yyyyMMddHHmmss");
        Map<String,String> map=new HashMap<>();
        map.put("functionCode",functionCode);
        map.put("channel",channel);
        map.put("id",id);
        map.put("platform",platform);
        map.put("timestamp",timestamp);
        map.put("key",Ksort(map));
        //String url="http://frp.o-learn.cn:51085/thirdparty/liveCourseMaintenance/register";
        String url="https://www.ty-sx.com/thirdparty/liveCourseMaintenance/registerUser";
        String post = HttpClientUtil.doPost(url, map);
        System.out.println("post:"+post);

    }


    //用户分享
    public static void addUserClass() {
        String functionCode="addUserClass";
        String classId="8a8880866e1a5c23016e1f663a28016b";
        String channel="channel";
        String mobilephone="16621242384";
        String platform="liveCourseConnect";
        String timestamp=DateFormatUtils.format(Calendar.getInstance().getTime(), "yyyyMMddHHmmss");
        Map<String,String> map=new HashMap<>();
        map.put("functionCode",functionCode);
        map.put("channel",channel);
        map.put("classId",classId);
        map.put("mobilephone",mobilephone);
        map.put("platform",platform);
        map.put("timestamp",timestamp);
        map.put("key",Ksort(map));
        String url="http://frp.o-learn.cn:51085/thirdparty/liveCourseMaintenance/addUserClass";
        String post = HttpClientUtil.doPost(url, map);
        System.out.println("post:"+post);
    }


    //8a8880866e1a5c23016e1b601986012d
    //添加录播课节
    public static void addRecordedLesson(){
        String functionCode="addRecordLesson";
        //String teacherId="8a8880866e1a5c23016e1b592a790126";
        String classId="402881ec6ef3f0c2016ef7a77f0c02a0";
        String title="视频标题";
        String code=UUID.randomUUID().toString();
        String coverImgLink="http://94.191.62.87:81/images/1.jpg";
        String vid="aef3afd3d09bbf9671712d08399945ee_a";
        String recordHour="111";
        String allowTaste="0";
        String videoDuration="100";
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
        String url="http://frp.o-learn.cn:51085/thirdparty/liveCourseMaintenance/addRecordLesson";
        String post = HttpClientUtil.doPost(url, map);
        System.out.println("post:"+post);
    }

    //8a8880866e1a5c23016e1f663a28016b
    //8a8880866e3ffb5c016e4065e2cb0058
    //添加录播课程
    public static void addRecordedClass(){
        String functionCode="addRecordClass";
        String name="测试课程1";
        String code= UUID.randomUUID().toString();
        String coverImgLink="http://94.191.62.87:81/images/1.jpg";
        String coverVid="aef3afd3d0e5dce8c20c66a3d55ed1de_a";

        String enrollStartDate="";
        String enrollEndDate="";
        //String expirationDuration="30";
        System.out.println(enrollStartDate);
        System.out.println(enrollEndDate);

        String classHour="10";
        String totalTime="100";
        String classType="素质教育";  //学科教育 素质教育 国际教育
        String recordType="1";
        String primeCost="666";
        String cost="666";
        String siteName="添翼申学";
        //String siteName="正承教育";
        String platform="liveCourseConnect";
        String timestamp= DateFormatUtils.format(Calendar.getInstance().getTime(), "yyyyMMddHHmmss");
        Map<String,String> map=new HashMap<>();
        String maxNum="100";
        //map.put("expirationDuration",expirationDuration);
        map.put("coverVid",coverVid);
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
        map.put("totalTime",totalTime);
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

        //正式地址:https://www.ty-sx.com/thirdparty/liveCourseMaintenance/addRecordClass
        //测试地址:http://frp.o-learn.cn:51085/thirdparty/liveCourseMaintenance/addRecordClass
        String url="http://frp.o-learn.cn:51085/thirdparty/liveCourseMaintenance/addRecordClass";
        //String url="https://www.ty-sx.com/thirdparty/liveCourseMaintenance/addRecordClass";
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
        String url="https://www.ty-sx.com/thirdparty/liveCourseMaintenance/getClassOrder";
        String post = HttpClientUtil.doPost(url, map);
        System.out.println("post:"+post);
        JSONObject jsonObject = JSONObject.parseObject(post);
        JSONArray returnData = jsonObject.parseArray(jsonObject.getString("returnData"));
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
        String url="https://www.ty-sx.com/thirdparty/liveCourseMaintenance/getClassDetail";
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

    //直播课节添加 ff8080816dba26a7016dbdf418501ae5
    public static void addLesson(){
        String functionCode="addLesson";
        //String teacherId="ff8080816dae664f016db99a9fb92e96";
        String classId="ff8080816dba269d016dbdeb10fd5878";
        String liveContent="直播内容";
        String code=UUID.randomUUID().toString();
        String liveStartDate="20191013110239";
        String liveEndDate="20191014110239";
        String lessonHour="10";
        String liveManNumber="15";
        String platform="liveCourseConnect";
        String timestamp=DateFormatUtils.format(Calendar.getInstance().getTime(), "yyyyMMddHHmmss");
        ;
        Map<String,String> map=new HashMap<>();
        map.put("functionCode",functionCode);
        //map.put("teacherId",teacherId);
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
        String url="https://www.ty-sx.com/thirdparty/liveCourseMaintenance/addLesson";
        String post = HttpClientUtil.doPost(url, map);
        System.out.println("post:"+post);
    }

    public static void addLiveClass() throws Exception {
        String functionCode="addLiveCourse";
        String name="测试课程1";
        String code= UUID.randomUUID().toString();
        String coverImgLink="http://94.191.62.87:81/images/1.jpg";
        String enrollStartDate=DateFormatUtils.format(Calendar.getInstance().getTime(), "yyyyMMddHHmmss");
        ;
        String enrollEndDate=DateFormatUtils.format(Calendar.getInstance().getTime(), "yyyyMMddHHmmss");
        ;
        String classHour="10";
        String classType="素质教育"; //学科教育 素质教育 国际教育
        String primeCost="666";
        String cost="666";
        String siteName="正承教育";
        String platform="liveCourseConnect";
        String timestamp= DateFormatUtils.format(Calendar.getInstance().getTime(), "yyyyMMddHHmmss");
        Map<String,String> map=new HashMap<>();
        String maxNum="100";
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
        map.put("courseIntroduceImg",getURLEncoderString(courseIntroduceImg));
        map.put("courseInformation",getURLEncoderString(courseInformation));
        map.put("courseTeachersHighlight",getURLEncoderString(courseTeachersHighlight));
        map.put("courseHighlight",getURLEncoderString(courseHighlight));
        map.put("courseLearningContent",getURLEncoderString(courseLearningContent));
        map.put("courseObservationStyle",getURLEncoderString(courseObservationStyle));
        map.put("courseConsultant",getURLEncoderString(courseConsultant));
        map.put("courseWarmPrompt",getURLEncoderString(courseWarmPrompt));
        String url="https://www.ty-sx.com/thirdparty/liveCourseMaintenance/addLiveClass";
        String post = HttpClientUtil.doPost(url,map);
        System.out.println("post:"+post);
    }

    //8a8880866e1a5c23016e1b592a790126
    public static void addTeacher() throws Exception {
        String functionCode="addTeacher";
        String userId="16621242385";
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
        userId = new SecurityDesCoder("tyxs9sx").encrypt("16621242385");
        map.put("userId",userId);
        map.put("siteName",getURLEncoderString(siteName));
        map.put("orient",getURLEncoderString(orient));
        map.put("note",getURLEncoderString(note));
        map.put("key",key);
        //http://frp.o-learn.cn:51085/hirdparty/
        //String url="https://www.ty-sx.com/thirdparty/liveCourseMaintenance/addTeacher";
        String url="http://frp.o-learn.cn:51085/thirdparty/liveCourseMaintenance/addTeacher";
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
        String appentStr=str+prefix;
        return MD5Util.getMD5(appentStr);
    }
}
