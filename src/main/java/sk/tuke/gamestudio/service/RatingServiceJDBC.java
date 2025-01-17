package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.game.consoleui.ConsoleUI;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RatingServiceJDBC implements RatingService {
    public static final String URL = "jdbc:postgresql://localhost/gamestudio";
    public static final String USER = "postgres";
    public static final String PASSWORD = "postgres";
    public static final String SELECT = "SELECT game, player, rating, ratedOn FROM rating WHERE game = ? --ORDER BY rating DESC LIMIT 10";
    public static final String SELECT_BY_ID = "SELECT game, player, rating, ratedOn FROM rating WHERE ident = ?";
    public static final String DELETE = "DELETE FROM rating";
    public static final String INSERT = "INSERT INTO rating (game, player, rating, ratedOn) VALUES (?, ?, ?, ?)";
    public static final String UPDATE = "UPDATE rating SET game = ?, player = ?, rating = ?, ratedOn = ? WHERE ident = ?";
    private ConsoleUI consoleUI;

    public void setConsoleUI(ConsoleUI consoleUI) {
        this.consoleUI = consoleUI;
    }

    @Override
    public void setRating(Rating rating) {
        if (rating.getIdent() != null) {
            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement statement = connection.prepareStatement(UPDATE)
            ) {
                statement.setString(1, rating.getGame());
                statement.setString(2, rating.getPlayer());
                statement.setInt(3, rating.getRating());
                statement.setTimestamp(4, new Timestamp(rating.getRatedOn().getTime()));
                statement.setInt(5, rating.getIdent());
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new ScoreException("Problem updating rating", e);
            }
        } else {
            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement statement = connection.prepareStatement(INSERT)
            ) {
                statement.setString(1, rating.getGame());
                statement.setString(2, rating.getPlayer());
                statement.setInt(3, rating.getRating());
                statement.setTimestamp(4, new Timestamp(rating.getRatedOn().getTime()));
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new ScoreException("Problem inserting rating", e);
            }
        }
    }

    @Override
    public List<Rating> getRating(String game) throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT);
        ) {
            statement.setString(1, game);
            try (ResultSet rs = statement.executeQuery()) {
                List<Rating> ratings = new ArrayList<>();
                while (rs.next()) {
                    ratings.add(new Rating(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getTimestamp(4)));
                }
                return ratings;
            }
        } catch (SQLException e) {
            throw new RatingException("Problem selecting rating", e);
        }
    }

    @Override
    public Rating getRatingById(int id) throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID);
        ) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return new Rating(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getTimestamp(4));
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RatingException("Problem selecting rating by id", e);
        }
    }

    @Override
    public void reset() throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate(DELETE);
        } catch (SQLException e) {
            throw new RatingException("Problem deleting rating", e);
        }
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        int totalRating = 0;
        int numberOfRatings = 0;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT);
        ) {
            statement.setString(1, game);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    totalRating += rs.getInt("rating");
                    numberOfRatings++;
                }
            }
        } catch (SQLException e) {
            throw new RatingException("Problem selecting ratings", e);
        }

        if (numberOfRatings == 0) {
            return 0;
        }

        return totalRating / numberOfRatings;
    }
}

