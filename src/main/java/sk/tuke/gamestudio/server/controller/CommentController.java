package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.service.CommentService;

import java.util.Date;

@RestController
public class CommentController {
    private String username = "unknown";

    private final CommentService commentService;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/save-comment")
    public void saveComment(@RequestBody String comment) {
//        Comment usercomment = new Comment("mastermind", "Linda", comment, new Date());
        Comment usercomment = new Comment("mastermind", getUsername(), comment, new Date());
        commentService.addComment(usercomment);
        System.out.println("Comment was added to database");
    }

    @PostMapping("/loggedUser")
    public String addName(@RequestBody String usernamed) {
        String usernameWithoutQuotes = usernamed.substring(1, usernamed.length() - 1);
        setUsername(usernameWithoutQuotes);


        return "Hello, " + usernamed;
    }
}

