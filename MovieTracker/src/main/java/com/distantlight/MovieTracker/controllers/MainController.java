package com.distantlight.MovieTracker.controllers;

import com.distantlight.MovieTracker.dtos.MovieFilterRequest;
import com.distantlight.MovieTracker.services.MovieFilterService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


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


    @GetMapping("/script")
    public ResponseEntity<Resource> getScript() throws IOException {

        Resource resource = new ClassPathResource("js/script.js");

        return ResponseEntity.ok()
                .contentLength(resource.contentLength())
                .contentType(MediaType.parseMediaType("text/javascript"))
                .body(resource);
    }
}
