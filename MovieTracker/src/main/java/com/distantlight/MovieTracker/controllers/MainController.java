package com.distantlight.MovieTracker.controllers;

import com.distantlight.MovieTracker.entities.MovieFilterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MainController {
    @PostMapping("/filter")
    public ResponseEntity<?> getMainPage(@RequestBody MovieFilterRequest movieFilterRequest){
        ArrayList<String> movieList = new ArrayList<>(List.of(movieFilterRequest.getText().split("\\n")));
        return ResponseEntity.ok(movieList);
    }
}
