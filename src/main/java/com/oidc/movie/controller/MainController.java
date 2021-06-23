package com.oidc.movie.controller;

import com.oidc.movie.dao.MovieMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@ComponentScan(basePackages="com.oidc.movie.dao")

@RestController
public class MainController {

    public String result = "";

    @Autowired
    private MovieMapper userMapper;

    @GetMapping("/test")
    public String test(){
        //userMapper.test();
        return "movie";
    }
}
