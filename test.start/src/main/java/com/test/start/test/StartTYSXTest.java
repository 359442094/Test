package com.test.start.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.test.start.test.bean.SendCourseAduitResult;
import com.test.start.test.util.HttpClientUtil;
import com.test.start.test.util.MD5Util;
import com.test.start.test.util.SecurityDesCoder;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author CJ
 * @date 2019/12/31
 */
public class StartTYSXTest {
    public static void main(String[] args) throws Exception {
        //TestTYSX.addTeacher();
        TestTYSX.editTeacher();

      // TestTYSX.addLiveClass();*/
        //TestTYSX.editLiveClass();

        //TestTYSX.addLesson();
        //TestTYSX.editLiveLesson();
        //TestTYSX.deleteLiveLesson();

        //TestTYSX.getClassDetail();
        //TestTYSX.getClassOrder();
        //TestTYSX.addRecordedClass();
        //TestTYSX.addRecordedLesson();

        //TestTYSX.addUserClass();
        //TestTYSX.deleteUserClass();

        //TestTYSX.addTySxUser();
        //添加机构
        //TestTYSX.addTyOrg();
        //添加渠道
        //TestTYSX.addTyChannel();
        //获取添加渠道结果
        //TestTYSX.addTyChannelResult();

        //sendCourseAduit("16621242383");

    }

    private static void sendCourseAduit(String phone){
        String url="http://192.168.0.182:8021/msg/audit?telNum="+phone;
        String json = HttpClientUtil.doPost(url, null);
        SendCourseAduitResult aduitResult = JSONObject.parseObject(json, SendCourseAduitResult.class);
        System.out.println("aduitResult:"+aduitResult);
    }

}
