package com.distantlight.MovieTracker.controllers;

import com.distantlight.MovieTracker.dtos.MovieFilterRequest;
import com.distantlight.MovieTracker.services.MovieFilterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MainController {
    private final MovieFilterService movieFilterService;

    public MainController(MovieFilterService movieFilterService) {
        this.movieFilterService = movieFilterService;
    }

    @PostMapping("/filter")
    public ResponseEntity<?> getFilteredMovies(@RequestBody MovieFilterRequest movieFilterRequest){
        return ResponseEntity.ok(movieFilterService.getFilteredMovies(movieFilterRequest));
    }
}
