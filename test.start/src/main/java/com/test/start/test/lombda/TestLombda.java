package com.test.start.test.lombda;

import java.util.Arrays;
import java.util.List;

/**
 * @author CJ
 * @date 2019/11/29
 */
public class TestLombda {

    public static void main(String[] args) {
        //方法无参无返回
        //function1();
        function3();
    }

    public static void function3(){
        List<String> strings = Arrays.asList("1", "2");
        strings.stream().sorted().forEach(System.out::println);
    }

    public static void function2() {

        TestInterface testInterface=(Integer a,Integer b)->{
            return a+b;
        };
        Integer integer = testInterface.process(1, 2);
        System.out.println("integer = " + integer);

    }

    //方法无参无返回
    public static void function1(){
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread Runnable run start");
            }
        }).start();*/
        new Thread(()->{
            System.out.println("true = " + true);
        }).start();
    }

}
