package com.oidc.movie.dao;

import com.oidc.movie.dto.MovieDto;

import java.util.List;

public interface MovieMapper {
    public String test();
    public List<MovieDto> getMovieRank();
    public List<MovieDto> getBPM(String title);
    public int getCount(String title);
    public String userLoveCheck(String title, String userId);
    public void userLoveDelete(String userId, String title);
    public void userLoveInsert(String userId, String title);
    public void insertMovieInfo(String title);
}
