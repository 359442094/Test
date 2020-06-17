package com.test.start.test.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

/**
 * @author CJ
 * @date 2020/6/17
 */
@Slf4j
public class ConsumerThreadManager implements Runnable{

    private CountDownLatch end;

    private String poolName;

    private int i;

    public ConsumerThreadManager() {}

    public ConsumerThreadManager(CountDownLatch end,String poolName,int i) {
        this.end = end;
        this.poolName = poolName;
        this.i = i;
    }

    @Override
    public void run() {
        log.info(this.poolName+":"+this.i);
        this.end.countDown();
        log.info("-------------------------"+this.end.getCount()+" end-------------------------");
    }

}
