package sg.edu.nus.iss.app.workshop26.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.nus.iss.app.workshop26.models.Game;
import sg.edu.nus.iss.app.workshop26.services.SearchBGGService;

@Controller
@RequestMapping(path = "/page/bgg")
public class SearchBGGController {

    @Autowired
    private SearchBGGService bggSvc;

    @GetMapping(path = "/games")
    public String getAllGame(Model model,
            @RequestParam Integer limit, @RequestParam Integer offset) {
        List<Game> results = bggSvc.searchGame(limit, offset);
        model.addAttribute("results", results);
        return "game-result";
    }
}
