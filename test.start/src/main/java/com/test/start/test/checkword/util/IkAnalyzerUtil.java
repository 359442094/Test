package com.test.start.test.checkword.util;

import com.test.start.test.checkword.analizer.ik.IKAnalyzer4Lucene7;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 中文分词器
 * @author CJ
 * @date 2020/2/26
 */
@Slf4j
@Component
public class IkAnalyzerUtil {

    private static Map<String,List<String>> doToken(String word,TokenStream tokenStream,List<String> ikAnalyzerWord) throws IOException {
        Map<String,List<String>> maps=new HashMap<>();
        tokenStream.reset();
        CharTermAttribute cta = tokenStream.getAttribute(CharTermAttribute.class);
        while(tokenStream.incrementToken()){
            if(cta!=null &&!StringUtils.isEmpty(cta.toString())){
                if(CheckUtil.isAlpha(cta.toString())){
                    ikAnalyzerWord.add(cta.toString().toUpperCase());
                    ikAnalyzerWord.add(cta.toString().toLowerCase());
                }else if(CheckUtil.isAlphaOne(cta.toString())){
                    String[] split = cta.toString().split("");
                    for (String s : split) {
                        if (!StringUtils.isEmpty(s) && s != null && s.matches("(?i)[^a-z]*[a-z]+[^a-z]*")) {
                            // 有字母
                            ikAnalyzerWord.add(cta.toString().replaceAll(s, s.toUpperCase()));
                        }
                    }
                }else{
                    ikAnalyzerWord.add(cta.toString());
                }
            }
        }
        maps.put(word,ikAnalyzerWord);
        tokenStream.end();
        tokenStream.close();
        return maps;
    }

    /**
     * 中文语句分词处理
     * @param word 当前文字信息
     * @param ikAnalyzerWord 过滤之后添加至集合列表
     */
    public static Map<String,List<String>> processIkAnalyzerWord(String word, List<String> ikAnalyzerWord){
        // IKAnalyzer 细粒度切分
        try (Analyzer analyzer = new IKAnalyzer4Lucene7();){
            TokenStream tokenStream = analyzer.tokenStream("content",word);
            return doToken(word,tokenStream,ikAnalyzerWord);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String text="12 兼zhi";
        processIkAnalyzerWord(text,new ArrayList<>());
    }

}
