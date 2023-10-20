package com.distantlight.MovieTracker.services;

import com.distantlight.MovieTracker.dtos.MovieFilterRequest;

import java.util.List;

public interface MovieFilterService {
    List<String> getFilteredMovies(MovieFilterRequest movieFilterRequest);
}
