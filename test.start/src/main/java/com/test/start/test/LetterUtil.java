package com.test.start.test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CJ
 * @date 2020/5/7
 */
public class LetterUtil {

    /**
     * 初始化字母序号
     * 生成A-Z大写|小写字母列表
     * @param isUppercase true:大写 false:小写
     * @return
     */
    public static List<String> letterItems(boolean isUppercase){
        List<String> letters=new ArrayList<>();
        int firstEnglish, lastEnglish;
        //获取首字母与末字母的值
        char firstE = 'A',lastE = 'Z';
        firstEnglish = (int)firstE;
        lastEnglish = (int)lastE;
        for(int i = firstEnglish; i <= lastEnglish; ++i)
        {
            if(isUppercase){
                //大写
                letters.add(String.valueOf((char)i));
            }else{
                //小写
                letters.add(String.valueOf((char)(i + 32)));
            }
        }
        return letters;
    }

}
