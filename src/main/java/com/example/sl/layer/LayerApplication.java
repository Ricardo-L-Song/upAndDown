package com.example.sl.layer;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication
@MapperScan(basePackages = "com.example.sl.layer.dao")//这个注解注意一下 放DAO层的包名 对这个包下进行注入
public class LayerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LayerApplication.class, args);
    }
}
