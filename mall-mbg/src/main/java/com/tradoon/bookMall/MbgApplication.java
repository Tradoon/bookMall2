package com.tradoon.bookMall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */
@SpringBootApplication
@MapperScan("com.tradoon.bookMall.dao")
public class MbgApplication {
    public static void main(String[] args) {
        SpringApplication.run(MbgApplication.class,args);
    }
}
