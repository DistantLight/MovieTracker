package com.distantlight.MovieTracker.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieFilterRequest {
    private String text;
    private List<String> requiredGenres;
}
