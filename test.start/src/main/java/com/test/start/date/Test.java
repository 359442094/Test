package com.test.start.date;

import com.test.start.test.util.DateUtil;
import org.apache.commons.lang.time.DateFormatUtils;

import javax.rmi.CORBA.Util;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 获取当前时间到月底之间的所有工作日
 * @author CJ
 * @date 2020/6/10
 */
public class Test {

    public static void main(String[] args) {
        //获取当前时间
        Date thisDate = Calendar.getInstance().getTime();
        //获取当前时间月底的最后一天
        int thisDateEndTime = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
        Calendar thisEndDate = Calendar.getInstance();

        thisEndDate.set(thisEndDate.get(Calendar.YEAR),thisEndDate.get(Calendar.MONTH),thisEndDate.getActualMaximum(Calendar.DAY_OF_MONTH),00,00,00);
        //获取两个日期间隔的所有日期
        List<String> dates = DateUtil.getBetweenDate(thisDate, thisEndDate.getTime());
        dates.stream().forEach(s -> System.out.println(s));

    }
}
