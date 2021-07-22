package com.oidc.movie.controller;

import com.oidc.movie.dao.MovieMapper;
import com.oidc.movie.dto.MovieDto;
import com.oidc.movie.parse.ParseMovie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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

    // 영화 인기 순위
    @GetMapping(path="/hotmovierank")
    public List<MovieDto> hotMovieRank(){

        ParseMovie parseMovie = new ParseMovie(movieMapper.getMovieRank());
        List<MovieDto> list = parseMovie.getHotMovieRank();

        return list;
    }

    // 영화 검색
    @GetMapping(path="/searchmovie")
    public List<MovieDto> searchmovie(@RequestParam String query){

        ParseMovie parseMovie = new ParseMovie(query);

        return parseMovie.getList();
    }

    // 영화 상세 정보
    @GetMapping(path = "/movieinfo")
    public List<MovieDto> movieinfo(@RequestParam String title, String userId){

        int max = 0, min = 0, count = 0, love = 1;

        List<MovieDto> bpm = movieMapper.getBPM(title);
        count = movieMapper.getCount(title);
        System.out.println(title);

        if(movieMapper.userLoveCheck(title,userId)==null){
            love = 0;
        }

        try{
            System.out.println(bpm.get(0).getMin());
            System.out.println(bpm.get(0).getMax());
            System.out.println(count);

            min = bpm.get(0).getMin();
            max = bpm.get(0).getMax();
        }catch(NullPointerException e) {
            System.out.println("데이터가 없습니다");
        }catch (IndexOutOfBoundsException e){
            System.out.println("데이터가 없습니다.");
        }

        ParseMovie parseMovie = new ParseMovie(title, min, max, count, love);

        return parseMovie.getInfo();
    }

    // 영화 좋아요 상태 변경
    @GetMapping(path = "/changelove")
    public String changeLove(@RequestParam String userId, String title, String state){

        System.out.println(userId);
        System.out.println(title);
        System.out.println(state);

        try{
            if(state.trim().equals("delete")){
                movieMapper.userLoveDelete(userId,title);
                result = "좋아요 취소";
            }
            else if(state.trim().equals("insert")){
                movieMapper.userLoveInsert(userId,title);
                movieMapper.insertMovieInfo(title);
                result = "좋아요";
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return result;
    }
}
