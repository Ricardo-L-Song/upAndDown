package com.example.sl.layer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("Index")
public class Index {
    public static final Logger logger=LoggerFactory.getLogger(Index.class);
    @RequestMapping("Index/layer")
    public ModelAndView hello(){
        logger.info("Hello world was canceled");
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/layer.jsp");
        modelAndView.addObject("message","this is my first spring mvc");//请求给渲染的页面传参
        return modelAndView;
    }

    @RequestMapping("Index/manager")
    public ModelAndView hello2(){
        logger.info("Hello world was canceled");
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/manager.jsp");
        modelAndView.addObject("message","this is my first spring mvc");//请求给渲染的页面传参
        return modelAndView;
    }

    @RequestMapping("/index")
    public ModelAndView hello3(){
        logger.info("Hello world was canceled");
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/index.jsp");
        return modelAndView;
    }

}