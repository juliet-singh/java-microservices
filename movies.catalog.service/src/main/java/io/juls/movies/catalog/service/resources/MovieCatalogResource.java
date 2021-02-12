package io.juls.movies.catalog.service.resources;

import io.juls.movies.catalog.service.models.CatalogItem;
import io.juls.movies.catalog.service.models.Movie;
import io.juls.movies.catalog.service.models.Rating;
import io.juls.movies.catalog.service.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")

public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){

        // RestTemplate restTemplate=new RestTemplate(); -- used @bean instead
        //WebClient.Builder builder=WebClient.builder(); --- used @bean instead

        //get all rated movie ids
       /* List<Rating> ratings = Arrays.asList(
             new Rating("123",4),
             new Rating("456",3)
        );*/

        UserRating ratings=restTemplate.getForObject("http://localhost:8083/ratingsdata/users/"+userId, UserRating.class);
        System.out.println(ratings);

        // for each movie id , get the movie detail - call movie info
       return ratings.getUserRating().stream().map(rating -> {
           Movie movie= restTemplate.getForObject("http://localhost:8082/movies/"+rating.getMovieId(), Movie.class);
           /*Movie movie=webClientBuilder.build()
                   .get()
                   .uri("http://localhost:8082/movies/\"+rating.getMovieId()")
                   .retrieve()
                   .bodyToMono(Movie.class)
                   .block();
            */

               return new CatalogItem(movie.getName(), "test", rating.getRating());
       })
       .collect(Collectors.toList());

        //put it all together
      //  return Collections.singletonList(
         //       new CatalogItem("Transformers", "test", 4)

    }
}
