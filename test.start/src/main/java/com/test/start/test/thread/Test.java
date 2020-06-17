package com.test.start.test.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * @author CJ
 * @date 2020/6/17
 */
@Slf4j
public class Test {

    public static void main(String[] args) {
        CountDownLatch end = new CountDownLatch(5);

        //ConsumerThreadManager consumerThreadManager=new ConsumerThreadManager(end,"test");

        ThreadPoolManager.startThreadPoolExecuor(5,end);
    }

}
