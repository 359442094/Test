package com.test.common.util;

import com.alibaba.excel.ExcelReader;
import com.xiaoleilu.hutool.crypto.SecureUtil;
import com.xiaoleilu.hutool.date.DateTime;
import com.xiaoleilu.hutool.date.DateUnit;
import com.xiaoleilu.hutool.dfa.SensitiveUtil;
import com.xiaoleilu.hutool.dfa.WordTree;
import com.xiaoleilu.hutool.http.HtmlUtil;
import com.xiaoleilu.hutool.io.FileUtil;
import com.xiaoleilu.hutool.io.IoUtil;
import com.xiaoleilu.hutool.lang.Validator;
import com.xiaoleilu.hutool.util.ThreadUtil;
import org.junit.Assert;
import org.springframework.aop.support.AopUtils;

import java.util.Date;
import java.util.List;

/**
 * @author CJ
 * @date 2020/3/31
 */
public class TestUtil {
    public static void main(String[] args) {
        WordTree tree = new WordTree();
        tree.addWord("大");
        tree.addWord("xxOO");
        tree.addWord("土豆");
        tree.addWord("刚出锅");
        tree.addWord("出锅");
        //正文
        String text = "我有一颗大xxOo，刚出锅的";
        List<String> matchAll = tree.matchAll(text, -1, true, true);

        System.out.println("matchAll:"+matchAll);
    }
}
