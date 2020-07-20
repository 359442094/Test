package com.test.start.test.classIn;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Charsets;
import com.sun.jndi.toolkit.url.Uri;
import com.test.start.test.util.HttpClientUtil;
import com.test.start.test.util.MD5Util;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import javax.swing.*;
import java.io.File;
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

    //生成配置参数
    /*private static final String sid="2540622";
    private static final String secret="68nTWV0g";*/

    private static final String sid="16111422";
    private static final String secret="ho8k5t7n";

    public void fuckyou(){
        int n = 0;
        Random rd = new Random();
        while (n<1) {
            JFrame frame = new JFrame("");
            frame.setSize(400,100);
            frame.setLocation(rd.nextInt(1920),rd.nextInt(1080));
            frame.setVisible(true);
            n++;
        }
    }

    public static void main(String[] args) throws Exception {
        //添加课程
        //addClassInCourse();
        //修改课程
        //updateClassInCourse();
        //添加课程下的多个|学生单个
        //addCourseStudentList();
        //删除课程下的多个|单个学生
        //deleteCourseStudentList();

        //添加单个课节(需要先添加用户+注册成教师 teacherUid=已经注册成为教师的用户编号)
        //addClassInLessionOne();

        //添加单个课节的多个|单个学生
        //addClassInLessionStudentList();
        //删除单个课节的多个|单个学生
        //deleteClassInLessionStudentList();

        //修改课节上台人数设置
        //modifyClassSeatNum();

        //添加教师
        //addTeacher();
        //注册用户
        //registerUser();

        //获取回放地址 https://www.eeo.cn/webcast.php?courseKey=7a30062bb9b028f9&lessonid=189128012
        //getClassInLive();

    }

    /**
     * 获取课程直播/回放播放器地址
     * https://www.eeo.cn/webcast_partner.html?courseKey=7a30062bb9b028f9&lessonid=189126496&account=16621242385&nickname=cj&checkCode=83a95f942c4e269aa5ab12c95e2e9c18
     * */
    public static void getClassInLive(){
        String timeStamp = String.valueOf((System.currentTimeMillis() + 60 * 10 * 1000) / 1000);
        Map<String, String> map = new HashMap<>();
        map.put("action","getWebcastUrl");
        map.put("SID", sid);
        map.put("safeKey", MD5Util.getMD5(secret + timeStamp).toLowerCase());
        map.put("timeStamp", timeStamp);
        map.put("courseId", "81176318");
        map.put("classId", "189128012");
        String json = HttpClientUtil.doPost(url, map);
        System.out.println("获取课程直播/回放播放器地址结果:"+json);

        Result result = JSONObject.parseObject(json, Result.class);

        String account="16621242385";

        String name="cj";

        System.out.println("result:"+result);

        String data = result.getData();
        String param = data.substring(data.lastIndexOf("?") + 1);
        String courseKey = data.substring(data.lastIndexOf("courseKey=") + 10, data.indexOf("&"));
        //String lessonId = data.substring(data.lastIndexOf("lessonid=") + 9);
        String checkCode = MD5Util.getMD5(secret + courseKey + account + name).toLowerCase();
        String webcastUrl = "https://www.eeo.cn/webcast_partner.html?" + param + "&account=" + account + "&nickname=" + name + "&checkCode=" + checkCode;
        System.out.println("webcastUrl:"+webcastUrl);

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
        map.put("courseId", "81176318");
        map.put("classId", "188966584");
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
     * 删除课节的单个|多个学生
     * */
    public static void deleteClassInLessionStudentList(){
        String timeStamp = String.valueOf((System.currentTimeMillis() + 60 * 10 * 1000) / 1000);
        Map<String, String> map = new HashMap<>();
        map.put("action","delClassStudentMultiple");
        map.put("SID", sid);
        map.put("safeKey", MD5Util.getMD5(secret + timeStamp).toLowerCase());
        map.put("timeStamp", timeStamp);
        map.put("courseId", "81176318");
        map.put("classId", "188966584");
        map.put("identity","1");
        List<String> students=new ArrayList<String>(){
            {
                add("23661786");
            }
        };
        map.put("studentUidJson",JSONObject.toJSONString(students));

        String json = HttpClientUtil.doPost(url, map);
        System.out.println("删除课节的单个|多个学生结果:"+json);

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
     * 生产:188930588
     * 测试:188964940、188966584、188966728、188966794、189126496、189128012
     */
    public static void addClassInLessionOne(){

        String courseId="84849293";
        //String courseId="81217306";
        String className="FUCK";

        //获取2020年06月10日 22：36：01的Date对象
        Calendar start = new GregorianCalendar(2020, 07, 15,22,36,01);
        Date startDate = start.getTime();
        System.out.println("start:"+ DateFormatUtils.format(startDate,"yyyy-MM-dd HH:mm:ss"));
        //获取2020年06月11日 22：36：01的Date对象
        Calendar end = new GregorianCalendar(2020, 07, 16,22,36,01);
        Date endDate = end.getTime();
        System.out.println("end:"+ DateFormatUtils.format(endDate,"yyyy-MM-dd HH:mm:ss"));

        String beginTime=String.valueOf(startDate.getTime() / 1000);
        String endTime=String.valueOf(endDate.getTime() / 1000);

        //String teacherUid="11749976";
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
        map.put("record", "1");
        map.put("live", "1");
        map.put("replay", "1");

        String json = HttpClientUtil.doPost(url, map);
        System.out.println("添加单个课节结果:"+json);

        Result result = JSONObject.parseObject(json, Result.class);

        System.out.println("result:"+result);

    }

    /**
     * 删除课程下的学生单个|多个
     * */
    public static void deleteCourseStudentList(){
        String timeStamp = String.valueOf((System.currentTimeMillis() + 60 * 10 * 1000) / 1000);
        Map<String, String> map = new HashMap<>();
        map.put("action","delCourseStudentMultiple");
        map.put("SID", sid);
        map.put("safeKey", MD5Util.getMD5(secret + timeStamp).toLowerCase());
        map.put("timeStamp", timeStamp);
        map.put("courseId", "81176318");
        map.put("identity", "1");
        List<String> students=new ArrayList<String>(){
            {
                add("23662506");
            }
        };
        map.put("studentUidJson",JSONObject.toJSONString(students));
        String json = HttpClientUtil.doPost(url, map);
        System.out.println("删除课程下的学生单个|多个结果:"+json);
        Result result = JSONObject.parseObject(json, Result.class);

        System.out.println("result:"+result);
    }

    /**
     * 添加课程下的学生单个|多个
     * */
    public static void addCourseStudentList(){
        String timeStamp = String.valueOf((System.currentTimeMillis() + 60 * 10 * 1000) / 1000);
        Map<String, String> map = new HashMap<>();
        map.put("action","addCourseStudentMultiple");
        map.put("SID", sid);
        map.put("safeKey", MD5Util.getMD5(secret + timeStamp).toLowerCase());
        map.put("timeStamp", timeStamp);
        map.put("courseId", "81176318");
        map.put("identity", "1");
        List<Student> students=new ArrayList<Student>(){
            {
                add(new Student("23674254"));
                add(new Student("12507218"));
            }
        };
        map.put("studentJson",JSONObject.toJSONString(students));
        String json = HttpClientUtil.doPost(url, map);
        System.out.println("添加课程下的学生多个结果:"+json);
        Result result = JSONObject.parseObject(json, Result.class);

        System.out.println("result:"+result);
    }

    /**
     * 修改单个课程
     * 生产:81166116、81175976
     * 测试:81176318
     * */
    public static void updateClassInCourse() throws Exception {
        Map<String,String> map=new HashMap<>();
        map.put("action","editCourse");
        map.put("SID",sid);
        String timeStamp = String.valueOf((System.currentTimeMillis() + 60 * 10 * 1000) / 1000);
        String safeKey = MD5Util.getMD5(secret+timeStamp).toLowerCase();
        map.put("safeKey",safeKey);
        map.put("timeStamp",timeStamp);
        map.put("courseId","84849293");
        map.put("courseName","testImage");
        //map.put("classroomSettingId","1");
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DATE,1);
        System.out.println("calendar:"+DateFormatUtils.format(calendar.getTime(),"yyyy-MM-dd HH:mm:ss"));
        long timeInMillis = calendar.getTimeInMillis();
        map.put("expiryTime", "1595260799");
        String path="C:\\phpstudy_pro\\WWW\\files\\timg (2).jpg";

        File file = new File(path);

        String json = HttpClientUtil.uploadFile(url,file,map);
        System.out.println("修改单个课程结果:"+json);

        Result result = JSONObject.parseObject(json, Result.class);

        System.out.println("result:"+result);
    }

    /**
     * 添加单个课程
     * 生产:81166116、81175976、81235408
     * 测试:81176318
     * */
    public static void addClassInCourse() throws Exception {
        Map<String,String> map=new HashMap<>();
        map.put("action","addCourse");
        map.put("SID",sid);
        String timeStamp = String.valueOf((System.currentTimeMillis() + 60 * 10 * 1000) / 1000);
        String safeKey = MD5Util.getMD5(secret+timeStamp).toLowerCase();
        map.put("safeKey",safeKey);
        map.put("timeStamp",timeStamp);
        map.put("courseName","testAddClassInCourse");
        //map.put("Filedata","http://94.191.62.87:81/images/1.jpg");

        String path="C:\\phpstudy_pro\\WWW\\files\\timg (2).jpg";

        File file = new File(path);

        String json = HttpClientUtil.uploadFile(url,file,map);
        System.out.println("添加单个课程结果:"+json);

        Result result = JSONObject.parseObject(json, Result.class);

        System.out.println("result:"+result);
    }

    /**
     * 注册用户
     * 生产:教师:9760018
     * 测试教师:11749976
     * 学生:
     * 11135664、23661786、23662450、23662506、23673872
     * 23674254、12507218
     */
    public static void registerUser(){
        String timeStamp = String.valueOf((System.currentTimeMillis() + 60 * 10 * 1000) / 1000);
        Map<String, String> map = new HashMap<>();
        map.put("action","register");
        map.put("SID", sid);
        map.put("safeKey", MD5Util.getMD5(secret + timeStamp).toLowerCase());
        map.put("timeStamp", timeStamp);
        //map.put("telephone", "13786482850");
        map.put("telephone", "16621242385");
        map.put("password","123456");
        String json = HttpClientUtil.doPost(url, map);
        System.out.println("添加用户结果:"+json);
        Result result = JSONObject.parseObject(json, Result.class);

        System.out.println("result:"+result);
    }

    /**
     * 添加教师
     * 生产教师:1444497
     * 测试教师:1447669、1450909
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
