package com.test.start.test.checkword.service.impl;

import com.test.start.test.checkword.service.TimedTaskService;
import com.test.start.test.checkword.service.WordKeyService;
import com.test.start.test.checkword.util.LuceneUtil;
import com.test.start.test.checkword.util.TimesTaskUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.cfg.DefaultConfig;
import org.wltea.analyzer.dic.Dictionary;
import java.util.List;

/**
 * @author CJ
 * @date 2020/6/18
 */
@Slf4j
@Service
public class TimedTaskServiceImpl implements TimedTaskService {

    @Autowired
    private WordKeyService wordKeyService;

    @Override
    public String flushLuceneIndex() throws Exception {
        long startTime = System.currentTimeMillis();
        //数据库中获取敏感词
        List<String> words = wordKeyService.getWordKeys();

        List<String> upperCaseWords = LuceneUtil.processWords(words);

        try {
            //动态更新分词字典配置文件
            Dictionary dictionary = Dictionary.initial(DefaultConfig.getInstance());
            dictionary.addWords(upperCaseWords);
        }catch (Exception e){
            log.error("更新分词字典配置文件load.dic失败:"+e);
        }finally {
            //更新lucene全文索引
            LuceneUtil.startWiter(upperCaseWords);
            long endTime = System.currentTimeMillis();
            return TimesTaskUtil.process(startTime, endTime);
        }
    }

}
