package com.distantlight.MovieTracker.services;

import com.distantlight.MovieTracker.dtos.MovieInfoRequestDto;
import com.distantlight.MovieTracker.dtos.MovieInfoResponseDto;
import com.distantlight.MovieTracker.dtos.MovieFilterRequest;
import com.distantlight.MovieTracker.repositrories.GenreDictionary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovieFilterServiceImpl implements MovieFilterService {
    private final RestTemplate restTemplate;
    private final String apiKey;
    private final String EXTERNAL_SERVICE_TEMPLATE_URL = "https://api.themoviedb.org/3/search/movie?api_key=%s&query=%s&year=%d";

    public MovieFilterServiceImpl(RestTemplate restTemplate, @Value("${external.api.key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
    }

    @Override
    public List<String> getFilteredMovies(MovieFilterRequest movieFilterRequest){
        List<String> movieList = getMovieName(movieFilterRequest);
        Set<Integer> requiredGenresIds = getRequiredGenreIds(movieFilterRequest);

        if(requiredGenresIds.isEmpty()){
            return movieList;
        }

        ArrayList<MovieInfoRequestDto> resultMovieList = new ArrayList<>();
        for(String movieName: movieList) {
            List<MovieInfoRequestDto> filteredMovieInfoRequestDtoList = filterMoviesByGenre(movieName, requiredGenresIds);
            resultMovieList.addAll(filteredMovieInfoRequestDtoList);
        }
        return resultMovieList.stream().map(o -> o.getTitle() + " " + o.getReleaseDate().getYear()).collect(Collectors.toCollection(ArrayList::new));
    }

    private List<String> getMovieName(MovieFilterRequest request) {
        return new ArrayList<>(List.of(request.getText().split("\\n")));
    }

    private Set<Integer> getRequiredGenreIds(MovieFilterRequest request) {
        List<String> requiredGenres = request.getRequiredGenres();
        return requiredGenres.stream()
                .map(genre -> GenreDictionary.getGenreDictionary().get(genre))
                .collect(Collectors.toSet());
    }

    private List<MovieInfoRequestDto> fetchMovieInfo(String movieName) {
        String title = movieName.substring(0, movieName.length() - 5);
        int year = Integer.parseInt(movieName.substring(movieName.length() - 4));

        String url = String.format(EXTERNAL_SERVICE_TEMPLATE_URL,
                apiKey, title, year);

        MovieInfoResponseDto response = restTemplate.getForObject(url, MovieInfoResponseDto.class);
        return response.getResults();
    }

    private List<MovieInfoRequestDto> filterMoviesByGenre(String movieName, Set<Integer> requiredGenresIds) {
        String title = movieName.substring(0, movieName.length() - 5);
        int year = Integer.parseInt(movieName.substring(movieName.length() - 4));

        String url = String.format("https://api.themoviedb.org/3/search/movie?api_key=%s&query=%s&year=%d",
                apiKey, title, year);

        MovieInfoResponseDto response = restTemplate.getForObject(url, MovieInfoResponseDto.class);
        List<MovieInfoRequestDto> movieInfoRequestDtoList = response.getResults();

        return movieInfoRequestDtoList.stream()
                .filter(o -> ((title.equals(o.getTitle()) || title.equals(o.getOriginalTitle()))
                        && (year == o.getReleaseDate().getYear())
                        && (new HashSet<>(o.getGenreIds()).containsAll(requiredGenresIds))))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
