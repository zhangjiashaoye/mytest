package com.topband.bluetooth.api;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}
        ,scanBasePackages = {"com.topband.bluetooth.common","com.topband.bluetooth.serviceImpl", "com.topband.bluetooth.api"})
@MapperScan("com.topband.bluetooth.dao.mapper")
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

}
