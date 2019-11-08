package com.test.common.annoation;

import org.apache.poi.ss.formula.functions.T;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CheckMethod {

    Class parms();

}
