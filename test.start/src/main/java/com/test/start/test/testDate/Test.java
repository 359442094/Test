package com.test.start.test.testDate;

import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;

/**
 * @author CJ
 * @date 2020/5/11
 */
public class Test {
    /**
     * 计算开始时间结束时间的毫秒数
     * @return
     */
    public static String computeTotalTimeStr(long start,long end) {
        String defaultStr="00:00:00";
        if(end>0&&end>0){
            long hm = end - start;
            if(hm>0){
                return millisToStringShort(hm,defaultStr);
            }
        }
        return defaultStr;
    }

    /**
     * 把毫秒数转换成时分秒
     * @param millis
     * @return
     */
    private static String millisToStringShort(long millis,String defaultStr) {
        StringBuffer strBuilder = new StringBuffer();
        long temp = millis;
        long hper = 60 * 60 * 1000;
        long mper = 60 * 1000;
        long sper = 1000;
        //时
        if (temp / hper > 0) {
            strBuilder.append(temp / hper).append("时");
        }else{
            strBuilder.append("0时");
        }
        temp = temp % hper;
        //分
        if (temp / mper > 0) {
            strBuilder.append(temp / mper).append("分");
        }else{
            strBuilder.append("0分");
        }
        temp = temp % mper;
        //秒
        if (temp / sper > 0) {
            strBuilder.append(temp / sper).append("秒");
        }else{
            strBuilder.append("0秒");
        }
        if(!StringUtils.isEmpty(strBuilder.toString())){
            return strBuilder.toString();
        }else{
            return defaultStr;
        }
    }

    public static void main(String[] args) throws Exception {
        //先将两个时间转换为毫秒相减，得到相差的毫秒数
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        long start = simpleDateFormat.parse("2020-05-11 10:05:05").getTime();
        long end = System.currentTimeMillis();
        String totalTimeStr = computeTotalTimeStr(start, end);
        System.out.println("totalTimeStr:"+totalTimeStr);
    }
}
