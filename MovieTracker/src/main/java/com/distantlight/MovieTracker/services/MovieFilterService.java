package com.distantlight.MovieTracker.services;

import com.distantlight.MovieTracker.dtos.MovieInfoDto;
import com.distantlight.MovieTracker.dtos.MovieInfoResponseDto;
import com.distantlight.MovieTracker.dtos.MovieFilterRequest;
import com.distantlight.MovieTracker.repositrories.GenreDictionary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovieFilterService {
    private final RestTemplate restTemplate;
    private final String apiKey;

    public MovieFilterService(RestTemplate restTemplate, @Value("${external.api.key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
    }

    public List<String> getFilterMovies(MovieFilterRequest movieFilterRequest){
        ArrayList<String> movieList = new ArrayList<>(List.of(movieFilterRequest.getText().split("\\n")));
        List<String> requiredGenres = movieFilterRequest.getRequiredGenres();
        Set<Integer> requiredGenresIds = requiredGenres.stream().map(o-> GenreDictionary.getGenreDictionary().get(o)).collect(Collectors.toSet());

        if(requiredGenres.isEmpty()){
            return movieList;
        }

        ArrayList<MovieInfoDto> resultMovieList = new ArrayList<>();
        for(String movieName: movieList) {
            String title = movieName.substring(0, movieName.length() - 5);
            int year = Integer.parseInt(movieName.substring(movieName.length() - 4));

            String url = String.format("https://api.themoviedb.org/3/search/movie?api_key=%s&query=%s&year=%d",
                    apiKey, title, year);

            MovieInfoResponseDto response = restTemplate.getForObject(url, MovieInfoResponseDto.class);
            List<MovieInfoDto> movieInfoDtoList = response.getResults();

            ArrayList<MovieInfoDto> filteredMovieInfoDtoList = movieInfoDtoList.stream()
                    .filter(o -> ((title.equals(o.getTitle()) || title.equals(o.getOriginalTitle()))
                            && (year == o.getReleaseDate().getYear())
                            && (new HashSet<>(o.getGenreIds()).containsAll(requiredGenresIds))))
                    .collect(Collectors.toCollection(ArrayList::new));

            resultMovieList.addAll(filteredMovieInfoDtoList);
        }
        return resultMovieList.stream().map(o -> o.getTitle() + " " + o.getReleaseDate().getYear()).collect(Collectors.toCollection(ArrayList::new));
    }
}
