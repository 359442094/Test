package com.test.start;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@ComponentScan(basePackages = {
        "com.test.model",
        "com.test.common",
        "com.test.service",
        "com.test.controller",
        "com.test.start"
})
@MapperScan(basePackages = {
        "com.test.model.persistence"
})
@SpringBootApplication
@EnableSwagger2
public class StartApp {
    public static void main(String[] args) {
        SpringApplication.run(StartApp.class);
    }
}
