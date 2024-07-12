package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.ScoreService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class MastermindController {
    private final ScoreService scoreService;
    private final CommentService commentService;
    private final RatingService ratingService;
    private int count = 0;

    @Autowired
    public MastermindController(ScoreService scoreService, CommentService commentService, RatingService ratingService) {
        this.scoreService = scoreService;
        this.commentService = commentService;
        this.ratingService = ratingService;

    }

    @GetMapping
    public String index(Model model) {
        this.count = 0;
        model.addAttribute("points", 11);

        return "index";
    }

    @GetMapping("/game")
    public String game(Model model) {
//        List<String> items = new ArrayList<>();
//        items.add("ahoj");
//        items.add("svet");

//        model.addAttribute("count", this.count);
//        model.addAttribute("items", items);

        return "level";
    }

    @GetMapping("/login")
    public String login(Model model) {
        List<String> items = new ArrayList<>();
        items.add("ahoj");
        items.add("svet");

        model.addAttribute("count", this.count);
        model.addAttribute("items", items);

        return "login";
    }


    @GetMapping("/registration")
    public String registration(Model model) {
        List<String> items = new ArrayList<>();
        items.add("ahoj");
        items.add("svet");

        model.addAttribute("count", this.count);
        model.addAttribute("items", items);

        return "registration";
    }

    @PostMapping("/newGame")
    public String newGame() {
        this.count++;

        return "redirect:/game";
    }

    @PostMapping("/login")
    public String login() {
        this.count++;
        return "redirect:/login";
    }

    @PostMapping("/registration")
    public String registration() {
        this.count++;
        return "redirect:/registration";
    }

    @PostMapping("/score")
    public String showScore(Model model) {
        List<Score> scores = this.scoreService.getTopScores("mastermind");
        model.addAttribute("scores", scores);

        return "score";
    }

    @PostMapping("/comments")
    public String showComments(Model model) {
        List<Comment> comments = this.commentService.getComments("mastermind");
        model.addAttribute("comments", comments);
        return "comment";
    }

    @PostMapping("/rating")
    public String showRating(Model model) {
        List<Rating> ratings = ratingService.getRating("mastermind");
        model.addAttribute("ratings", ratings);

        int averageRating = ratingService.getAverageRating("mastermind");
        model.addAttribute("averageRating", averageRating);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        IntStream.range(0, ratings.size()).forEach(i -> {
            Rating rating = ratings.get(i);
            System.out.println((i + 1) + ". " + rating.getPlayer() + "\t" + rating.getRating() + "\t" + sdf.format(rating.getRatedOn()));
        });

        return "rating";
    }

}
