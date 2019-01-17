package com.example.sl.layer.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@RestController
@Configuration
public class DefaultView {

    @RequestMapping("/")
    public ModelAndView hello111(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/login.jsp");
        return modelAndView;
    }
}