package com.example.webstudy.web;

import com.example.webstudy.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class IndexContoller {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("posts", postsService.findAllDesc());
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
