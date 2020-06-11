package com.test.start.test.classIn;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Charsets;
import com.sun.jndi.toolkit.url.Uri;
import com.test.start.test.util.HttpClientUtil;
import com.test.start.test.util.MD5Util;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * classIn:创建课程->创建课节->添加课节下的学生->删除课节下的学生
 * @author CJ
 * @date 2020/6/10
 */
public class Test {

    private static final String url="https://api.eeo.cn/partner/api/course.api.php?action=";

    private static final String sid="2540622";

    private static final String secret="68nTWV0g";

    public static void main(String[] args) throws Exception {
        //添加课程
        //addClassInCourse();
        //添加单个课节
        //addClassInLessionOne();

        //添加单个课节的多个|单个学生
        //addClassInLessionStudentList();
        //删除单个课节的多个|单个学生
        //deleteClassInLessionStudentList();
        //修改上台人数设置
        modifyClassSeatNum();

        //添加教师
        //addTeacher();
        //注册用户
        //registerUser();
    }

    /**
     * 修改上台人数设置
     * */
    public static void modifyClassSeatNum(){
        String timeStamp = String.valueOf((System.currentTimeMillis() + 60 * 10 * 1000) / 1000);
        Map<String, String> map = new HashMap<>();
        map.put("action","modifyClassSeatNum");
        map.put("SID", sid);
        map.put("safeKey", MD5Util.getMD5(secret + timeStamp).toLowerCase());
        map.put("timeStamp", timeStamp);
        map.put("courseId", "81166116");
        map.put("classId", "188930588");
        //最多只能修改为机构最大上台学生数，大于机构最大上台人数时会默认为机构最大上台人数(12)
        //上台人数0-6个支持高清、6个以上不支持高清
        map.put("seatNum","6");
        //0=非高清，1=高清，2=全高清，非1的数字都会当做0来处理
        map.put("isHd","2");
        String json = HttpClientUtil.doPost(url, map);
        System.out.println("修改上台人数结果:"+json);

        Result result = JSONObject.parseObject(json, Result.class);

        System.out.println("result:"+result);
    }

    /**
     * 删除单个|多个课节的学生
     * */
    public static void deleteClassInLessionStudentList(){
        String timeStamp = String.valueOf((System.currentTimeMillis() + 60 * 10 * 1000) / 1000);
        Map<String, String> map = new HashMap<>();
        map.put("action","delClassStudentMultiple");
        map.put("SID", sid);
        map.put("safeKey", MD5Util.getMD5(secret + timeStamp).toLowerCase());
        map.put("timeStamp", timeStamp);
        map.put("courseId", "81166116");
        map.put("classId", "188930588");
        map.put("identity","1");
        List<String> students=new ArrayList<String>(){
            {
                add("23662506");
            }
        };
        map.put("studentUidJson",JSONObject.toJSONString(students));

        String json = HttpClientUtil.doPost(url, map);
        System.out.println("删除单个|多个课节的学生结果:"+json);

        Result result = JSONObject.parseObject(json, Result.class);

        System.out.println("result:"+result);
    }

    /**
     * 添加课节的单个|多个学生
     */
    public static void addClassInLessionStudentList(){
        String timeStamp = String.valueOf((System.currentTimeMillis() + 60 * 10 * 1000) / 1000);
        Map<String, String> map = new HashMap<>();
        map.put("action","addClassStudentMultiple");
        map.put("SID", sid);
        map.put("safeKey", MD5Util.getMD5(secret + timeStamp).toLowerCase());
        map.put("timeStamp", timeStamp);
        map.put("courseId", "81166116");
        map.put("classId", "188930588");
        map.put("identity","1");
        List<Student> students=new ArrayList<Student>(){
            {
                add(new Student("23662506"));
            }
        };
        map.put("studentJson",JSONObject.toJSONString(students));

        String json = HttpClientUtil.doPost(url, map);
        System.out.println("添加课节的单个|多个学生结果:"+json);

        Result result = JSONObject.parseObject(json, Result.class);

        System.out.println("result:"+result);
    }

    /**
     * 添加单个课节
     * 188930588
     */
    public static void addClassInLessionOne(){

        String courseId="81166116";
        String className="test1";

        //获取2020年06月10日 22：36：01的Date对象
        Calendar start = new GregorianCalendar(2020, 06, 10,22,36,01);
        Date startDate = start.getTime();
        System.out.println("start:"+ DateFormatUtils.format(startDate,"yyyy-MM-dd HH:mm:ss"));
        //获取2020年06月11日 22：36：01的Date对象
        Calendar end = new GregorianCalendar(2020, 06, 11,22,36,01);
        Date endDate = end.getTime();
        System.out.println("end:"+ DateFormatUtils.format(endDate,"yyyy-MM-dd HH:mm:ss"));

        String beginTime=String.valueOf(startDate.getTime() / 1000);
        String endTime=String.valueOf(endDate.getTime() / 1000);

        String teacherUid="9760018";

        Map<String,String> map=new HashMap<>();
        map.put("action","addCourseClass");
        map.put("SID",sid);
        String timeStamp = String.valueOf((System.currentTimeMillis() + 60 * 10 * 1000) / 1000);
        String safeKey = MD5Util.getMD5(secret+timeStamp).toLowerCase();
        map.put("safeKey",safeKey);
        map.put("timeStamp",timeStamp);
        map.put("courseId",courseId);
        map.put("className",className);
        map.put("beginTime",beginTime);
        map.put("endTime",endTime);
        map.put("teacherUid",teacherUid);

        String json = HttpClientUtil.doPost(url, map);
        System.out.println("添加单个课节结果:"+json);

        Result result = JSONObject.parseObject(json, Result.class);

        System.out.println("result:"+result);

    }

    /**
     * 添加单个课程
     * 81166116
     * */
    public static void addClassInCourse() throws Exception {
        Map<String,String> map=new HashMap<>();
        map.put("action","addCourse");
        map.put("SID",sid);
        String timeStamp = String.valueOf((System.currentTimeMillis() + 60 * 10 * 1000) / 1000);
        String safeKey = MD5Util.getMD5(secret+timeStamp).toLowerCase();
        map.put("safeKey",safeKey);
        map.put("timeStamp",timeStamp);
        map.put("courseName","test");

        String json = HttpClientUtil.doPost(url, map);
        System.out.println("添加单个课程结果:"+json);

        Result result = JSONObject.parseObject(json, Result.class);

        System.out.println("result:"+result);
    }

    /**
     * 注册用户
     * 教师:9760018
     * 学生:11135664、23661786、23662450、23662506
     */
    public static void registerUser(){
        String timeStamp = String.valueOf((System.currentTimeMillis() + 60 * 10 * 1000) / 1000);
        Map<String, String> map = new HashMap<>();
        map.put("action","register");
        map.put("SID", sid);
        map.put("safeKey", MD5Util.getMD5(secret + timeStamp).toLowerCase());
        map.put("timeStamp", timeStamp);
        map.put("telephone", "13786482850");
        map.put("password","123456");
        String json = HttpClientUtil.doPost(url, map);
        System.out.println("添加用户结果:"+json);
        Result result = JSONObject.parseObject(json, Result.class);

        System.out.println("result:"+result);
    }

    /**
     * 添加教师
     * 教师:1444497
     */
    public static void addTeacher(){
        String timeStamp = String.valueOf((System.currentTimeMillis() + 60 * 10 * 1000) / 1000);
        Map<String, String> map = new HashMap<>();
        map.put("action","addTeacher");
        map.put("SID", sid);
        map.put("safeKey", MD5Util.getMD5(secret + timeStamp).toLowerCase());
        map.put("timeStamp", timeStamp);
        //map.put("teacherUid", teacherBean.getTeacherUid());
        map.put("teacherAccount", "16621242385");
        map.put("teacherName", "cj");
        String json = HttpClientUtil.doPost(url, map);
        System.out.println("添加老师结果:"+json);
        Result result = JSONObject.parseObject(json, Result.class);

        System.out.println("result:"+result);
    }

}
