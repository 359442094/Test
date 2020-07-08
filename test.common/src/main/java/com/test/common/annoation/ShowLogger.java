package com.test.common.annoation;

import java.lang.annotation.*;

/**
 * 显示日志注解
 * */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ShowLogger {

    String info();

}
