package com.test.start.test;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author CJ
 * @date 2019/12/13
 */
@Slf4j
public class TestCao {
    public static void main(String[] args) {

        String str="法油草泥马我日尼玛";

        List<String> keys= Arrays.asList("尼玛","法克","泥马");
        List<String> filters = keys.stream().filter(key -> str.contains(key)).collect(Collectors.toList());//.forEach(key->log.info(key));
        filters.forEach(System.out::println);

    }
}
