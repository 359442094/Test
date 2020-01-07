package com.test.start.test;

import org.apache.tomcat.util.threads.ThreadPoolExecutor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author CJ
 * @date 2019/12/31
 */
public class ThreadPoolSerialTest {
    public static void main(String[] args) {
        //核心线程数
        int corePoolSize = 3;
        //最大线程数
        int maximumPoolSize = 6;
        //超过 corePoolSize 线程数量的线程最大空闲时间
        long keepAliveTime = 2;
        //以秒为时间单位
        TimeUnit unit = TimeUnit.SECONDS;
        //创建工作队列，用于存放提交的等待执行任务
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(2);
        ThreadPoolExecutor threadPoolExecutor = null;
        try {
            //创建线程池
            threadPoolExecutor = new ThreadPoolExecutor(corePoolSize,
                    maximumPoolSize,
                    keepAliveTime,
                    unit,
                    workQueue,
                    new ThreadPoolExecutor.AbortPolicy());

            //循环提交任务
            System.out.println("111111111111");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPoolExecutor.shutdown();
        }
    }
}
