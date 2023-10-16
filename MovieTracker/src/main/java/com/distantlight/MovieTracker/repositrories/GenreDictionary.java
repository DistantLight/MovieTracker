package com.distantlight.MovieTracker.repositrories;

import lombok.Getter;

import java.util.HashMap;


public class GenreDictionary {
    @Getter
    private static final HashMap<String, Integer> genreDictionary = new HashMap<>();

    static {
        genreDictionary.put("Action", 28);
        genreDictionary.put("Adventure", 12);
        genreDictionary.put("Animation", 16);
        genreDictionary.put("Comedy", 35);
        genreDictionary.put("Crime", 80);
        genreDictionary.put("Documentary", 99);
        genreDictionary.put("Drama", 18);
        genreDictionary.put("Family", 10751);
        genreDictionary.put("Fantasy", 14);
        genreDictionary.put("History", 36);
        genreDictionary.put("Horror", 27);
        genreDictionary.put("Music", 10402);
        genreDictionary.put("Mystery", 9648);
        genreDictionary.put("Romance", 10749);
        genreDictionary.put("Science Fiction", 878);
        genreDictionary.put("TV Movie", 10770);
        genreDictionary.put("Thriller", 53);
        genreDictionary.put("War", 10752);
        genreDictionary.put("Western", 37);
    }
}
