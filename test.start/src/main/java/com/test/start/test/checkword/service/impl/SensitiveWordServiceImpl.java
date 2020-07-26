package com.test.start.test.checkword.service.impl;

import com.test.common.util.LuceneUtil;
import com.test.start.test.checkword.dto.SensitiveWordFilterRequest;
import com.test.start.test.checkword.dto.SensitiveWordFilterResponse;
import com.test.start.test.checkword.service.SensitiveWordService;
import com.test.start.test.checkword.util.IkAnalyzerUtil;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 敏感词过滤
 * @author CJ
 * @date 2020/2/24
 */
@SuppressWarnings(value = "all")
@Service
@Transactional(rollbackFor = Exception.class)
public class SensitiveWordServiceImpl implements SensitiveWordService {


    private static String errorPrefix="含有敏感词汇";
    private static String errorSuffix="请您重新输入。";
    private static String successResult="输入正常，未包含敏感词汇。";

    /*@PostConstruct
    private void init(){
        impl=this;
        impl.words = getWords();
    }*/

    @Override
    public SensitiveWordFilterResponse filter(SensitiveWordFilterRequest request) throws ParseException {
        //数据库中获取敏感词设置为自定义分词字典内容
        //获取用户输入
        List<String> words = request.getWords();
        List<String> ikAnalyzerWord=new ArrayList<>();
        //添加之后的词语去重
        words = words.stream().distinct().collect(Collectors.toList());
        //定义检查结果对象
        SensitiveWordFilterResponse response=new SensitiveWordFilterResponse();
        //定义检查结果对象的反馈信息列表
        List<SensitiveWordFilterResponse.SensitiveWordFilter> filterInfos=new ArrayList<>();
        SensitiveWordFilterResponse.SensitiveWordFilter filterInfo=null;
        for (String word : words) {
            //单个过滤
            filterInfo = new SensitiveWordFilterResponse().new SensitiveWordFilter();
            filterInfo.setCheckWord(word);
            //数据库检索方式
            //boolean flag = SensitiveWordFilter(word);
            //Lucene检索方式
            boolean flag = LuceneUtil.startSearch(word);
            filterInfo.setFlag(flag);
            if(flag){
                filterInfo.setMessage(successResult);
                if(!StringUtils.isEmpty(word)){
                    //单个过滤正常之后进行分词过滤
                    Map<String, List<String>> maps = IkAnalyzerUtil.processIkAnalyzerWord(word, ikAnalyzerWord);
                    Set<String> ids = maps.keySet();
                    for (String id : ids) {
                        List<String> list = maps.get(id);
                        for (String key : list) {
                            boolean search = LuceneUtil.startSearch(key);
                            if(!search){
                                //检索到非法敏感字符跳出循环
                                filterInfo.setTextFilter(key);
                                filterInfo.setFlag(search);
                                filterInfo.setMessage(errorPrefix+"["+key+"]，"+errorSuffix);
                                break;
                            }
                        }
                    }
                }
            }else{
                filterInfo.setTextFilter(word);
                filterInfo.setMessage(errorPrefix+"["+word+"]，"+errorSuffix);
            }
            filterInfos.add(filterInfo);
        }
        response.setResults(filterInfos);
        return response;
    }

    /*@Override
    public SensitiveWordFilterResponse toolFilter(SensitiveWordFilterRequest request) {
        //获取用户输入
        List<String> texts = request.getWords().stream().distinct().collect(Collectors.toList());
        WordTree tree=new WordTree();
        tree.addWords(impl.words);
        //定义检查结果对象
        SensitiveWordFilterResponse response=new SensitiveWordFilterResponse();
        //定义检查结果对象的反馈信息列表
        List<SensitiveWordFilterResponse.SensitiveWordFilter> filterInfos=new ArrayList<>();
        SensitiveWordFilterResponse.SensitiveWordFilter filterInfo=null;
        for (String word : texts) {
            if(!StringUtils.isEmpty(word)){
                filterInfo=new SensitiveWordFilterResponse().new SensitiveWordFilter();
                List<String> results = tree.matchAll(word, -1, true, true);
                filterInfo.setTextFilter(word);
                if(results.size()>0){
                    //检索到非法敏感字符跳出循环
                    filterInfo.setFlag(false);
                    filterInfo.setMessage(errorPrefix+"["+results.get(0)+"]，"+errorSuffix);
                    break;
                }else{
                    filterInfo.setFlag(true);
                    filterInfo.setMessage(successResult);
                }
            }
        }
        filterInfos.add(filterInfo);
        response.setResults(filterInfos);
        return response;
    }

    @Override
    public SensitiveWordFilterResponse toolFilter(String word) {
        List<String> words=new ArrayList<>();
        words.add(word);
        return toolFilter(new SensitiveWordFilterRequest(words));
    }*/

}
