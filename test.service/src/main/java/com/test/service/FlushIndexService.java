package com.test.service;

public interface FlushIndexService {

    /**
     * 定时更新全文检索敏感词
     */
    String flushLuceneIndex() throws Exception;

}
