package com.itheima;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.itheima.mapper")
public class QkApplication {
    public static void main(String[] args) {
        SpringApplication.run(QkApplication.class, args);
    }
}
