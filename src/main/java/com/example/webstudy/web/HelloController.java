package com.example.webstudy.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public  String hello(){
        return "hello";
    }

    @GetMapping("/hi")
    public String hi(){

        return "hi spring";
    }
}
