package com.test.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: StringToolUtils
 *
 * @author: huangsheng
 * @date: 2018/12/12 14:31
 * @Copyright: 2018 www.vcredit.com Inc. All rights reserved.
 */
public class StringToolUtils {


    //首字母转大写
    public static String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    /**
     * 遍历list 拼接成 "str1|str2|str3|str4" 字符
     *
     * @param list
     * @param length 字符最大长度
     * @return
     */
    public static String listToStr(List<String> list, int length) {
        String searchedOrgType = "";
        for (String str : list) {
            if (org.apache.commons.lang3.StringUtils.isNotBlank(str)) {
                searchedOrgType = searchedOrgType + "|" + str;
            }
        }
        while (searchedOrgType.length() > 255) {
            searchedOrgType = searchedOrgType.substring(0, searchedOrgType.lastIndexOf("|"));
        }
        return searchedOrgType;
    }

    /**
     * 遍历以 "|" 拼接的字符串 存入list
     *
     * @param
     * @return
     */
    public static List<String> strToList(String key) {
        List<String> resultList = new ArrayList<>();
        String[] split = key.split("\\|");
        for (String str : split) {
            if (org.apache.commons.lang3.StringUtils.isNotBlank(str)) {
                resultList.add(str);
            }
        }
        return resultList;
    }

}
