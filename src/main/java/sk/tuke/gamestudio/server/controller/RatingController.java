package sk.tuke.gamestudio.server.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.RatingService;

import java.util.Date;

@RestController
public class RatingController {

    private final RatingService ratingService;
    private String username = "unknown";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    public record SendRatingRequest(@JsonProperty("rating") int rating, @JsonProperty("id") Integer id) {
    }

    @PostMapping("/sendRating")
    public int sendRating(@RequestBody SendRatingRequest request) {
        Rating userrating;
        if (request.id != null) {
            userrating = ratingService.getRatingById(request.id);
            userrating.setRating(request.rating);
        } else {
            userrating = new Rating("mastermind", getUsername(), request.rating, new Date());
        }
        ratingService.setRating(userrating);
        System.out.println("Rating was added to database");
        return userrating.getIdent();
    }

    @PostMapping("/loggedUserRating")
    public String addName(@RequestBody String usernamed) {
        String usernameWithoutQuotes = usernamed.substring(1, usernamed.length() - 1);
        setUsername(usernameWithoutQuotes);

        return "Hello, " + usernamed;
    }
}
