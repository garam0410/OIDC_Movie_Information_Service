package com.oidc.movie.dao;

import com.oidc.movie.dto.MovieDto;

import java.util.List;

public interface MovieMapper {
    public String test();
    public List<MovieDto> getMovieRank();
}
