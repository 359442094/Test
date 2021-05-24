package com.test.start;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@ComponentScan(basePackages = {
        "com.test.model",
        "com.test.common",
        "com.test.service",
        "com.test.controller",
        "com.test.start",
        "com.test.start.test"
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

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

}
