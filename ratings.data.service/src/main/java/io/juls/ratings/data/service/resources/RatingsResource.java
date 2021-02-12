package io.juls.ratings.data.service.resources;

import io.juls.ratings.data.service.models.Rating;
import io.juls.ratings.data.service.models.UserRating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratingsdata")
public class RatingsResource {
    @RequestMapping("/users/{userId}")
    public UserRating getUserRating(@PathVariable("userId") String userId)
    {
        List<Rating> ratings = Arrays.asList(
                new Rating("123",4),
                new Rating("456",3)
        );
        UserRating userRating=new UserRating();
        userRating.setUserRating(ratings);
        return userRating;
    }

    @RequestMapping("/{movieId}")
    public Rating getRating(@PathVariable("movieId") String movieId){
        return new Rating(movieId,4);
    }
}
