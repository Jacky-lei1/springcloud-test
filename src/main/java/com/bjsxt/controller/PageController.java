package com.bjsxt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面跳转控制层
 * @author lvyelanshan
 * @create 2019-12-05 10:46
 */
@Controller
public class PageController {

    @RequestMapping("/")
    public String showPage(){
        return "Login";
    }
    @RequestMapping("/{page}")
    public String showPage2(@PathVariable String page){
        return page;
    }

}
