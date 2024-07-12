package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.ScoreService;

import java.util.Date;

@RestController
public class ScoreController {

    private String username = "unknown";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private final ScoreService scoreService;

    @Autowired
    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @PostMapping("/sendScore")
    public void sendScore(@RequestBody int points) {
//        Score score = new Score("mastermind", "NOWSCORE", points, new Date());
        Score score = new Score("mastermind", getUsername(), points, new Date());
        scoreService.addScore(score);
        System.out.println("Score was added to database");
    }

    @PostMapping("/loggedUserScore")
    public String addName(@RequestBody String usernamed) {
        String usernameWithoutQuotes = usernamed.substring(1, usernamed.length() - 1);
        setUsername(usernameWithoutQuotes);


        return "Hello, " + usernamed;
    }
}

