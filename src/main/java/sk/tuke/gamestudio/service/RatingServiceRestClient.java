package sk.tuke.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.Rating;

import java.util.Arrays;
import java.util.List;

public class RatingServiceRestClient implements RatingService {
    private final String url = "http://localhost:8080/api/rating";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void setRating(Rating rating) {
        restTemplate.postForEntity(url, rating, Rating.class);
    }

    @Override
    public int getAverageRating(String game) {
        String averageRatingUrl = url + "/" + game + "/average";
        Integer averageRating = restTemplate.getForObject(averageRatingUrl, Integer.class);
        return averageRating != null ? averageRating : 0;
    }

    @Override
    public List<Rating> getRating(String gameName) {
        return Arrays.asList(restTemplate.getForEntity(url + "/" + gameName, Rating[].class).getBody());
    }

    @Override
    public Rating getRatingById(int id) throws RatingException {
        return restTemplate.getForEntity(url + "/by-id/" + id, Rating.class).getBody();
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported via web service");
    }
}
