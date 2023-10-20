package com.distantlight.MovieTracker.component;

import com.distantlight.MovieTracker.config.TestConfig;
import com.distantlight.MovieTracker.dtos.MovieInfoResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.springframework.test.util.AssertionErrors.fail;

@SpringBootTest(classes = TestConfig.class)
public class ExternalServiceConnectionTest {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${external.api.key}")
    private String apiKey;

    @Test
    void connectToService() throws IOException {
        //given
        String title = "The Good, the Bad and the Ugly";
        int year = 1966;
        String url = String.format("https://api.themoviedb.org/3/search/movie?api_key=%s&query=%s&year=%d",
                apiKey, title, year);

        //when and then
        try {
            restTemplate.getForObject(url, MovieInfoResponseDto.class);
        } catch (ResourceAccessException e) {
            fail("unable to connect");
        }
    }
}
