package com.ljh.jhoj;

import com.ljh.jhoj.config.OjConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@MapperScan("com.ljh.jhoj.mapper")
@SpringBootApplication
public class OnlinejudgeApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(OnlinejudgeApplication.class, args);
        OjConfiguration configuration = context.getBean(OjConfiguration.class);
        System.out.println(configuration);
    }
}


