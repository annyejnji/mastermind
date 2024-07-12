package sk.tuke.gamestudio.server.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
//@RequestMapping("")
public class LevelController {

    @GetMapping("/new-game")
    public ModelAndView startNewLevel(@RequestParam(name = "number") int number) {
        ModelAndView modelAndView;

        modelAndView = new ModelAndView("game");
        modelAndView.addObject("level", number + 2);
        return modelAndView;
    }

}
