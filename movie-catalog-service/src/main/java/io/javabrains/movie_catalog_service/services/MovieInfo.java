package io.javabrains.movie_catalog_service.services;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.javabrains.movie_catalog_service.models.CatalogItem;
import io.javabrains.movie_catalog_service.models.Movie;
import io.javabrains.movie_catalog_service.models.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieInfo {
    @Autowired
    private RestTemplate restTemplate;

    @CircuitBreaker(name = "movieInfo", fallbackMethod = "getFallbackCatalogItem")
    public CatalogItem getCatalogItem(Rating rating) {
        Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
        return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
    }

    public CatalogItem getFallbackCatalogItem(Rating rating, Throwable throwable) {
        return new CatalogItem("Movie name not found", "", rating.getRating());
    }
}
