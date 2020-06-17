package com.test.start.test.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * 线程池管理类
 * @author cj
 */
@Slf4j
public class ThreadPoolManager {

    private CountDownLatch end;

    /**
     * 获取线程池
     * corePoolSize    线程池核心池的大小
     * maximumPoolSize 线程池中允许的最大线程数量
     * keepAliveTime   当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间
     * unit            keepAliveTime 的时间单位(空闲的线程的存活时间s)
     * workQueue       用来储存等待执行任务的队列
     * threadFactory   创建线程的工厂类
     * handler         拒绝策略类,当线程池数量达到上线并且workQueue队列长度达到上限时就需要对到来的任务做拒绝处理
     *
     * @param poolSize 线程池核心池的大小
     * @param maxPoolSize 线程池中允许的最大线程数量
     * @param queueSize 用来储存等待执行任务的队列长度
     * @param poolName 线程池的名称
     */
    public static ExecutorService getThreadPoolManager(int poolSize, int maxPoolSize, int queueSize, String poolName) {
        return new ThreadPoolExecutor(
                poolSize,
                maxPoolSize,
                60,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(queueSize),
                //创建指定名称的线程池
                new ThreadFactoryBuilder().setNameFormat(poolName).build(),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

    /**
     * 使用线程池创建线程并异步执行任务
     */
    public static void startThreadPoolExecuor(int size,CountDownLatch end) {
        ExecutorService taskExecutor = ThreadPoolManager.getThreadPoolManager(3, 3, 3, "test");
        for(int i=1;i<=5;i++){
            if(i<2){
                ConsumerThreadManager manager=new ConsumerThreadManager(end,"test1",i);
                taskExecutor.execute(manager);
            }else if(i<=4&&i<5){
                ConsumerThreadManager manager=new ConsumerThreadManager(end,"test2",i);
                taskExecutor.execute(manager);
            }else{
                ConsumerThreadManager manager=new ConsumerThreadManager(end,"test3",i);
                taskExecutor.execute(manager);
            }
        }
        try {
            end.await();
        } catch (InterruptedException e) {
            log.error("startThreadPoolExecuor exception msg:{}", e);
            e.printStackTrace();
        }
        taskExecutor.shutdown();
    }

}