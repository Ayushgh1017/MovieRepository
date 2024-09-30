package io.javabrains.movie_catalog_service.controllers;


import io.javabrains.movie_catalog_service.models.CatalogItem;
import io.javabrains.movie_catalog_service.models.UserRating;
import io.javabrains.movie_catalog_service.services.MovieInfo;
import io.javabrains.movie_catalog_service.services.UserRatingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;



import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    MovieInfo movieInfo;

    @Autowired
    UserRatingInfo userratingInfo;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
        UserRating userRating = userratingInfo.getUserRating(userId);
        return userRating.getRatings().stream()
                .map(rating -> movieInfo.getCatalogItem(rating))
                .collect(Collectors.toList());

    }
}
