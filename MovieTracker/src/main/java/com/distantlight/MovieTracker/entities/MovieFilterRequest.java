package com.distantlight.MovieTracker.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovieFilterRequest {
    private String text;
}
