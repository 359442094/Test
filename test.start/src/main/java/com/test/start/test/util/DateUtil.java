package com.test.start.test.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DateUtil {

    /**
     * 获取两个日期间隔的所有日期
     *
     * @param start 格式必须为'2018-01-25'
     * @param end   格式必须为'2018-01-25'
     * @return
     */
    public static List<String> getBetweenDate(String start, String end) {
        List<String> list = new ArrayList<>();
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);

        long distance = ChronoUnit.DAYS.between(startDate, endDate);
        if (distance < 0) {
            return list;
        }
        Stream.iterate(startDate, d -> {
            return d.plusDays(1);
        }).limit(distance + 1).forEach(f -> {
            list.add(f.toString());
        });
        ListSort(list);
        List<String> collect = list.stream().distinct().sorted().collect(Collectors.toList());
        return collect;
    }

    /**
     * 用户输入星期数转换为系统星期数
     */
    private static Map<Integer, Integer> convertIntMap = new HashMap<Integer, Integer>() {
        {
            put(1, 2);
            put(2, 3);
            put(3, 4);
            put(4, 5);
            put(5, 6);
            put(6, 7);
            put(7, 1);
        }
    };


    /**
     * 获取上周周几的日期,默认一周从周一开始
     *
     * @param dayOfWeek
     * @param weekOffset
     * @return
     */
    public static Date getDayOfWeek(int dayOfWeek, int weekOffset) {
        return getDayOfWeek(Calendar.MONDAY, dayOfWeek, weekOffset);
    }

    /**
     * 获取上、下、本周指定星期数的日期
     *
     * @param firstDayOfWeek
     * @param dayOfWeek      {@link Calendar}
     * @param weekOffset     周偏移，上周为-1，本周为0，下周为1，以此类推
     * @return
     */
    public static Date getDayOfWeek(int firstDayOfWeek, int dayOfWeek, int weekOffset) {
        if (dayOfWeek > Calendar.SATURDAY || dayOfWeek < Calendar.SUNDAY) {
            return null;
        }
        if (firstDayOfWeek > Calendar.SATURDAY || firstDayOfWeek < Calendar.SUNDAY) {
            return null;
        }
        Calendar date = Calendar.getInstance(Locale.CHINA);
        date.setFirstDayOfWeek(firstDayOfWeek);
        // 周数减一，即上周
        date.add(Calendar.WEEK_OF_MONTH, weekOffset);
        // 日子设为周几
        date.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        // 时分秒全部置0
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        return date.getTime();
    }

    /**
     * 获取一天中间隔五分钟的所有时间点
     *
     * @return
     */
    public static List<String> getTimeMinute(int startHour, int endHour, Integer hr, int minutes) {
        ArrayList<String> list = new ArrayList<>();//创建集合存储所有时间点
        for (int h = 0, m = 0; h < 24; h += hr, m += minutes) {//创建循环，指定间隔分钟
            if (m > 60) {//判断分钟累计到60时清零，小时+1
                h++;
                m = m - 60;
            }
            if (m == 60) {//判断分钟累计到60时清零，小时+1
                h++;
                m = 0;
            }
            if (h > 24) {//判断小时累计到24时跳出循环，不添加到集合
                break;
            }
            if (h <= endHour && h >= startHour) {
                /*转换为字符串*/
                String hour = String.valueOf(h);
                String minute = String.valueOf(m);

                /*判断如果为个位数则在前面拼接‘0’*/
                if (hour.length() < 2) {
                    hour = "0" + hour;
                }
                if (minute.length() < 2) {
                    minute = "0" + minute;
                }
                list.add(hour + ":" + minute);//拼接为HH:mm格式，添加到集合
            }
        }
        return list;
    }

    //判断是否在规定的时间内 nowTime指定时间 beginTime开始时间 endTime结束时间
    public static boolean timeCalendar(Date nowTime, Date beginTime, Date endTime) {
        //设置当前时间
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);
        //设置开始时间
        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);
        //设置结束时间
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        //处于开始时间之后，和结束时间之前的判断
        if ((date.after(begin) && date.before(end))) {
            return true;
        }
        return false;
        /*
        //设置当前时间
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);
        //设置开始时间
        Calendar amBegin = Calendar.getInstance();
        amBegin.setTime(amBeginTime);//上午开始时间
        //设置结束时间
        Calendar amEnd = Calendar.getInstance();
        amEnd.setTime(amEndTime);
        Calendar pmEnd = Calendar.getInstance();
        //处于开始时间之后，和结束时间之前的判断
        if ((date.after(amBegin) && date.before(amEnd))) {
            return true;
        }
        return false;*/
    }

    public static void main(String[] args) throws ParseException {
        Calendar start = Calendar.getInstance();
        start.set(start.get(Calendar.YEAR), start.get(Calendar.MONTH), start.get(Calendar.DAY_OF_MONTH), 00, 00, 00);
        Calendar end = Calendar.getInstance();
        end.set(end.get(Calendar.YEAR), end.get(Calendar.MONTH), end.get(Calendar.DAY_OF_MONTH), 23, 59, 59);

        Calendar thisDate = Calendar.getInstance();
        //thisDate.add(Calendar.DAY_OF_YEAR,-1);
        thisDate.set(thisDate.get(Calendar.YEAR), thisDate.get(Calendar.MONTH), thisDate.get(Calendar.DAY_OF_MONTH), 18, 00, 00);


        boolean flag = timeCalendar(thisDate.getTime(), start.getTime(), end.getTime());
        System.out.println("flag:"+flag);

    }

    private static void ListSort(List<String> list) {
        Collections.sort(list, new Comparator<String>() {
            @Override
            //定义一个比较器
            public int compare(String o1, String o2) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date dt1 = format.parse(o1);
                    Date dt2 = format.parse(o2);
                    if (dt1.getTime() > dt2.getTime()) {
                        return 1;
                    } else if (dt1.getTime() < dt2.getTime()) {
                        return -1;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }

    public static void getDates(int minute, int startHour, int endHour, String date1, String date2) {
        Set<Set<Map<String, Object>>> o = new HashSet<>();

        List<String> betweenDate = getBetweenDate(date1, date2);
        for (int i1 = 1; i1 <= 2; i1++) {

            Set<Map<String, Object>> lists = new HashSet<>();

            int hAuto = minute / 60;
            int mAuto = minute % 60;

            ListSort(betweenDate);

            for (String date : betweenDate) {
                System.out.println("date:" + date);

                Map<String, Object> map = new HashMap<>();

                LocalDate startDate = LocalDate.parse(date);
                Calendar start = Calendar.getInstance();
                Calendar end = Calendar.getInstance();
                start.set(startDate.getYear(), startDate.getMonthValue(), startDate.getDayOfMonth(), startHour, 00, 00);
                end.set(startDate.getYear(), startDate.getMonthValue(), startDate.getDayOfMonth(), startHour, 00, 00);

                String startDateType = DateFormatUtils.format(start.getTime(), "yyyy-MM-dd");

                List<String> timeMinute = getTimeMinute(startHour, endHour, hAuto, mAuto);

                List<Map<String, String>> childMaps = new ArrayList<>();
                for (int i = 0; i < timeMinute.size() - 2; i++) {
                    Map<String, String> map1 = new HashMap<>();
                    map1.put("value", timeMinute.get(i) + "-" + timeMinute.get(i + 1));
                    map1.put("label", timeMinute.get(i) + "-" + timeMinute.get(i + 1));
                    childMaps.add(map1);
                }
                map.put("children", childMaps);
                map.put("value", startDateType);
                map.put("label", startDateType);
                lists.add(map);
            }
            o.add(lists);
        }



        System.out.println("dates:" + JSONObject.toJSONString(o));

    }

}
