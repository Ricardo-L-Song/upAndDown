package com.example.sl.layer;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@MapperScan(basePackages = "com.example.sl.layer.dao")//这个注解注意一下 放DAO层的包名 对这个包下进行注入
class LayerApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(LayerApplication.class, args);
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(LayerApplication.class);
    }
}
//public class LayerApplication {
//
//    public static void main(String[] args) {
//        SpringApplication.run(LayerApplication.class, args);
//    }
//}
//
//@SpringBootApplication



