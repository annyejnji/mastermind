package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Rating;

import java.util.List;

public interface RatingService {
    void setRating(Rating rating) throws RatingException;

    int getAverageRating(String game) throws RatingException;

    List<Rating> getRating(String game) throws RatingException;

    Rating getRatingById(int id) throws RatingException;

    void reset() throws RatingException;
}
