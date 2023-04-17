package com.bugjeogbugjeog.app.bugjeogbugjeog.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor
@Slf4j
public class IndexController {
    @GetMapping("/")    //url 부분
    public String mainPage1(){
        return "/main/main";
    }

    @GetMapping("/main")    //url 부분
    public String mainPage2(){
        return "/main/main";
    }
}
