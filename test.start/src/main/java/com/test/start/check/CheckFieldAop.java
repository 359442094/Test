package com.test.start.check;

import com.test.common.annoation.CheckField;
import com.test.common.annoation.CheckMethod;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author CJ
 * @date 2019/10/30
 */
@Component
@Aspect
@Slf4j
public class CheckFieldAop {

    @Pointcut(value = "@annotation(com.test.common.annoation.CheckMethod)")
    public void pointcut(){}

    @Before(value = "pointcut()")
    public void process(JoinPoint point) throws Exception {
        MethodSignature methodSignature= (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        if(method.isAnnotationPresent(CheckMethod.class)){
            CheckMethod checkMethod = method.getAnnotation(CheckMethod.class);
            Class aClass = checkMethod.parms();
            Class[] parameterTypes = method.getParameterTypes();
            for (Class parameterType : parameterTypes) {
                Field[] fields = parameterType.getDeclaredFields();
                for (Field field : fields) {
                    if(field.isAnnotationPresent(CheckField.class)){
                        CheckField checkField = field.getAnnotation(CheckField.class);
                        log.info("field："+field.getName());
                        Method method1 = aClass.getMethod("getId");
                        Object invoke = method1.invoke(aClass);
                        log.info("invoke:"+invoke);
                    }
                }
            }
        }
    }

}
