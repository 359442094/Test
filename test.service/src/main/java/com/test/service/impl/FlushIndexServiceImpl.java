//package com.test.service.impl;
//
//import com.test.common.util.TimesTaskUtil;
//import com.test.service.FlushIndexService;
//import com.test.model.domain.MtcSalesOrder;
//import com.test.model.domain.MtcSalesOrderExample;
//import com.test.model.persistence.MtcSalesOrderMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.wltea.analyzer.lucene.IKAnalyzer;
//
//import java.util.List;
//
//@Service
//public class FlushIndexServiceImpl implements FlushIndexService {
//
//    @Autowired
//    private MtcSalesOrderMapper mtcSalesOrderMapper;
//
//    @Override
//    public String flushLuceneIndex() throws Exception {
//        List<MtcSalesOrder> mtcSalesOrders = mtcSalesOrderMapper.selectByExample(new MtcSalesOrderExample());
//        long startTime = System.currentTimeMillis();
//        //数据库中获取敏感词
//        //更新lucene全文索引
//        LuceneUtil.startWiter(mtcSalesOrders);
//        long endTime = System.currentTimeMillis();
//        return TimesTaskUtil.process(startTime, endTime);
//    }
//}
