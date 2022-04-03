package com.example.webstudy.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class IndexContoller {

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/thymeleaf")
    public String thymelead(){
        return "layout/config";
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }
}
