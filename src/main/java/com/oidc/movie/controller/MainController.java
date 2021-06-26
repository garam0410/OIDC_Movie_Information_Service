package com.oidc.movie.controller;

import com.oidc.movie.dao.MovieMapper;
import com.oidc.movie.dto.MovieDto;
import com.oidc.movie.parse.ParseMovie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@ComponentScan(basePackages="com.oidc.movie.dao")

@RestController
public class MainController {

    public String result = "";

    @Autowired
    private MovieMapper movieMapper;

    @GetMapping("/test")
    public String test(){
        //userMapper.test();
        return "movie";
    }

    @GetMapping(path="/searchmovie")
    public List<MovieDto> searchmovie(@RequestParam String query){

        ParseMovie parseMovie = new ParseMovie(query);

        return parseMovie.getList();
    }
}
