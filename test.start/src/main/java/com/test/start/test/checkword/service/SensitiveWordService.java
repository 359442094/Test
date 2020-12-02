package com.test.start.test.checkword.service;

import com.test.start.test.checkword.dto.SensitiveWordFilterRequest;
import com.test.start.test.checkword.dto.SensitiveWordFilterResponse;
import org.apache.lucene.queryparser.classic.ParseException;

public interface SensitiveWordService  {

    /**
     * Lucene检索方式 忽略大小写
     * @param request
     * @return
     * @throws ParseException
     */
    SensitiveWordFilterResponse filter(SensitiveWordFilterRequest request) throws ParseException;

    /* *//**
     * Tool检索方式 不忽略大小写
     * @param request
     * @return
     *//*
    SensitiveWordFilterResponse toolFilter(SensitiveWordFilterRequest request);
    *//**
     * Tool检索方式 不忽略大小写
     * @param word
     * @return
     *//*
    SensitiveWordFilterResponse toolFilter(String word);*/

}
