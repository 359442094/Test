package com.test.start.test.checkword.service.impl;

import com.test.model.domain.WordKey;
import com.test.model.domain.WordKeyExample;
import com.test.model.persistence.WordKeyMapper;
import com.test.start.test.checkword.service.WordKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author CJ
 * @date 2020/6/18
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class WordKeyServiceImpl implements WordKeyService {

    @Autowired
    private WordKeyMapper wordKeyMapper;

    @Override
    public List<String> getWordKeys() {
        //List<WordKey> wordKeys = wordKeyMapper.selectList(new QueryWrapper<>());
        //return wordKeys.stream().map(wordKey -> wordKey.getWordKey()).collect(Collectors.toList());
        List<WordKey> wordKeys = wordKeyMapper.selectByExample(new WordKeyExample());
        return wordKeys.stream().map(wordKey -> wordKey.getWordKey()).collect(Collectors.toList());
    }

}
