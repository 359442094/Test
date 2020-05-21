package com.test.start.test;

import org.apache.commons.lang3.time.DateFormatUtils;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author CJ
 * @date 2020/5/20
 */
public class TestTm {

    private final static List<Integer> arrs = new ArrayList<Integer>() {
        {
            add(3);
            add(1);
            add(2);
            add(7);
            add(5);
        }
    };

    private final static List<Integer> arrs1 = new ArrayList<Integer>() {
        {
            add(3);
            add(1);
            add(7);
            add(2);
            add(5);
        }
    };

    public static void test1(List<Integer> arrs) {
        for (int i = 0; i < arrs.size(); i++) {
            for (int j = 0; j < i; j++) {
                if (arrs.get(i) < arrs.get(j)) {
                    int temp = arrs.get(j);
                    arrs.set(j, arrs.get(i));
                    arrs.set(i, temp);
                }
            }
        }
        arrs.stream().forEach(System.out::println);
    }

    public static String Min() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, +1);
        Date time = calendar.getTime();
        return DateFormatUtils.format(time, "yyyy-MM-dd HH:mm:ss");
    }

    public static void main(String[] args) {
        //test1(arrs);
        /*List<Integerdssda> collect = arrs.stream().sorted().collect(Collectors.toList());
        System.out.println("collect:"+collect);*/

        List<Integer> texts = arrs.stream().distinct().sorted().collect(Collectors.toList());
        List<Integer> keys = arrs1.stream().distinct().sorted().collect(Collectors.toList());

        boolean equals = texts.toString().equals(keys.toString());

        System.out.println("equals:"+equals);

    }

}
