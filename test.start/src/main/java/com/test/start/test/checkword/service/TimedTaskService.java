package com.test.start.test.checkword.service;

public interface TimedTaskService {

    /**
     * 定时更新全文检索敏感词
     */
    String flushLuceneIndex() throws Exception;

}
