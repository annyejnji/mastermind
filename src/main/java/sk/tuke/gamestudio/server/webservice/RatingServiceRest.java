package sk.tuke.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.RatingService;

import java.util.List;

@RestController
@RequestMapping("/api/rating")
public class RatingServiceRest {

    @Autowired
    private RatingService ratingService;

    @GetMapping("/{game}")
    public List<Rating> getRatings(@PathVariable String game) {
        return ratingService.getRating(game);
    }

    @GetMapping("/by-id/{id}")
    public Rating getRatings(@PathVariable int id) {
        return ratingService.getRatingById(id);
    }

    @GetMapping("/{game}/average")
    public int getAverageRating(@PathVariable String game) {
        return ratingService.getAverageRating(game);
    }

    @PostMapping
    public void addRatings(@RequestBody Rating rating) {
        ratingService.setRating(rating);
    }

    @DeleteMapping
    public void deleteRatings() {
        ratingService.reset();
    }
}
