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

    public static List<String> getBetweenDate(Date start, Date end) {
        String pattern = "yyyy-MM-dd";
        return getBetweenDate(DateFormatUtils.format(start, pattern), DateFormatUtils.format(end, pattern));
    }

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
        return list.stream().distinct().collect(Collectors.toList());
    }

    /**
     * 获取一天中间隔五分钟的所有时间点
     *
     * @return
     */
    public static List<String> getTimeMinute(int startHour, int endHour, Integer hr, int minutes) {
        ArrayList<String> list = new ArrayList<>();//创建集合存储所有时间点
        String minute = null;
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
            if (h < endHour && h >= startHour) {
                /*转换为字符串*/
                String hour = String.valueOf(h);
                minute = String.valueOf(m);

                /*判断如果为个位数则在前面拼接‘0’*/
                if (hour.length() == 1) {
                    hour = "0" + hour;
                }
                if (minute.length() == 1) {
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
    }

    public static void main(String[] args) throws ParseException {
        Calendar start = Calendar.getInstance();
        start.set(start.get(Calendar.YEAR), start.get(Calendar.MONTH), start.get(Calendar.DAY_OF_MONTH), 00, 00, 00);
        Calendar end = Calendar.getInstance();
        end.set(end.get(Calendar.YEAR), end.get(Calendar.MONTH), end.get(Calendar.DAY_OF_MONTH), 23, 59, 59);

        Calendar thisDate = Calendar.getInstance();
        //thisDate.add(Calendar.DAY_OF_YEAR,-1);
        thisDate.set(thisDate.get(Calendar.YEAR), thisDate.get(Calendar.MONTH), thisDate.get(Calendar.DAY_OF_MONTH), 18, 00, 00);

        getDates(5, 1, 10, start.getTime(), end.getTime());

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

    /**
     * @param minute    间隔分钟数
     * @param startHour 开始时间
     * @param endHour   结束时间
     * @param date1     开始日期
     * @param date2     结束日期
     */
    public static void getDates(int minute, int startHour, int endHour, Date date1, Date date2) {
        Set<Set<Map<String, Object>>> o = new HashSet<>();

        List<String> betweenDate = getBetweenDate(date1, date2);

        Set<Map<String, Object>> lists = new HashSet<>();

        int hAuto = minute / 60;
        int mAuto = minute % 60;

        ListSort(betweenDate);

        for (String date : betweenDate) {
            Map<String, Object> map = new HashMap<>();

            LocalDate startDate = LocalDate.parse(date);
            Calendar start = Calendar.getInstance();
            Calendar end = Calendar.getInstance();
            start.set(startDate.getYear(), startDate.getMonthValue(), startDate.getDayOfMonth(), startHour, 00, 00);
            end.set(startDate.getYear(), startDate.getMonthValue(), startDate.getDayOfMonth(), startHour, 00, 00);

            String startDateType = DateFormatUtils.format(start.getTime(), "yyyy-MM-dd");

            List<String> timeMinute = getTimeMinute(startHour, endHour, hAuto, mAuto);

            //timeMinute.add(timeMinute.get(timeMinute.size()-1)+":");

            List<Map<String, String>> childMaps = new LinkedList<>();
            for (int i = 0; i < timeMinute.size() - 1; i++) {
                Map<String, String> map1 = new HashMap<>();
                map1.put("value", timeMinute.get(i) + "-" + timeMinute.get(i + 1));
                childMaps.add(map1);
            }
            map.put("children", childMaps);
            map.put("value", startDateType);
            lists.add(map);

            Map<String, String> mapLast = new HashMap<>();

            lists.stream().forEach(stringObjectMap -> {
                LinkedList<Map<String, String>> o1 = (LinkedList<Map<String, String>>) stringObjectMap.get("children");
                String str = "" + (endHour - 1);
                str = str.length() == 1 ? "0" + str : str;
                mapLast.put("value",str + ":55" + "-" + endHour + ":00");
                o1.add(mapLast);
            });

        }

        o.add(lists);

        System.out.println("dates:" + JSONObject.toJSONString(o));

    }

}
