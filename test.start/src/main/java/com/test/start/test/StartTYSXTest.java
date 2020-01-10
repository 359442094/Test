package com.test.start.test;

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
        //TestTYSX.addLiveClass();
        //TestTYSX.addLesson();
        //TestTYSX.getClassDetail();
        //TestTYSX.getClassOrder();
        //TestTYSX.addRecordedClass();
        //TestTYSX.addRecordedLesson();
        //TestTYSX.addUserClass();
        //TestTYSX.addTySxUser();
        //添加机构
        //TestTYSX.addTyOrg();
        //添加渠道
        //TestTYSX.addTyChannel();
        //获取添加渠道结果
        TestTYSX.addTyChannelResult();
    }

}
