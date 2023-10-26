package com.distantlight.MovieTracker.integration.EndToEnd;

import com.distantlight.MovieTracker.config.TestConfig;
import com.distantlight.MovieTracker.controllers.MainController;
import com.distantlight.MovieTracker.dtos.MovieFilterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = TestConfig.class)
class MovieFilterTest {
    @Autowired
    private MainController mainController;

    @Test
    public void getFilteredMovies(){
        //given
        MovieFilterRequest movieFilterRequest = new MovieFilterRequest();
        movieFilterRequest.setText("Arizona 2018\n" +
                "The Good, the Bad and the Ugly 1966\n" +
                "Apocalypse Now 1977");
        movieFilterRequest.setRequiredGenres(List.of("Western"));

        //when
        ResponseEntity<?> filteredMoviesResponse = mainController.getFilteredMovies(movieFilterRequest);

        //then
        assertEquals(200, filteredMoviesResponse.getStatusCode().value());
        assertEquals(List.of("The Good, the Bad and the Ugly 1966"), filteredMoviesResponse.getBody());
    }

    @Test
    public void getFilteredMoviesWithEmptyMovieList(){
        //given
        MovieFilterRequest movieFilterRequest = new MovieFilterRequest();
        movieFilterRequest.setText("");
        movieFilterRequest.setRequiredGenres(List.of("Western"));

        //when and then
        assertThrows(StringIndexOutOfBoundsException.class, () -> mainController.getFilteredMovies(movieFilterRequest));
    }

    @Test
    public void getFilteredMoviesWithEmptyGenresList(){
        //given
        MovieFilterRequest movieFilterRequest = new MovieFilterRequest();
        movieFilterRequest.setText("Arizona 2018\n" +
                "The Good, the Bad and the Ugly 1966\n" +
                "Apocalypse Now 1977");
        movieFilterRequest.setRequiredGenres(new ArrayList<>());

        //when
        ResponseEntity<?> filteredMoviesResponse = mainController.getFilteredMovies(movieFilterRequest);

        //then
        assertEquals(200, filteredMoviesResponse.getStatusCode().value());
        assertEquals(List.of("Arizona 2018", "The Good, the Bad and the Ugly 1966", "Apocalypse Now 1977"), filteredMoviesResponse.getBody());
    }
}