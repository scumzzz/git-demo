package com.vueblog;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.vueblog.mapper")
public class VueblogApplication {
    public static void main(String[] args) {
        SpringApplication.run(VueblogApplication.class, args);
    }
}
