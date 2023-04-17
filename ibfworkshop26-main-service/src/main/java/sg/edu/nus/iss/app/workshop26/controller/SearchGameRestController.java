package sg.edu.nus.iss.app.workshop26.controller;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import sg.edu.nus.iss.app.workshop26.models.Comment;
import sg.edu.nus.iss.app.workshop26.models.Game;
import sg.edu.nus.iss.app.workshop26.models.Games;
import sg.edu.nus.iss.app.workshop26.services.SearchBGGService;

@RestController
@RequestMapping(path = "/api/bgg")
public class SearchGameRestController {

    @Autowired
    private SearchBGGService bggSvc;

    @GetMapping(path = "/games")
    public ResponseEntity<String> getAllGame(
            @RequestParam Integer limit, @RequestParam Integer offset) {
        List<Game> results = bggSvc.searchGame(limit, offset);
        JsonObject result = null;
        // Build the result
        JsonObjectBuilder objBuilder = Json.createObjectBuilder();
        Games gs = new Games();
        gs.setGames(results);
        gs.setTotal(results.size());
        gs.setLimit(limit);
        gs.setOffset(offset);
        gs.setTimestamp(DateTime.now());

        objBuilder.add("bgg", gs.toJSON());
        result = objBuilder.build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toString());
    }

    @GetMapping(path = "/games/rank")
    public ResponseEntity<String> getGameByRanking() {
        JsonArray result = null;
        List<Game> results = bggSvc.getGamesByRank();
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        for (Game g : results)
            arrBuilder.add(g.toJSON());
        result = arrBuilder.build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toString());
    }

    @GetMapping(path = "/games/{gameId}")
    public ResponseEntity<String> getGameDetailsById(
            @PathVariable Integer gameId) {
        JsonObject result = null;
        System.out.println("gameId" + gameId);
        Game gameDetails = bggSvc.getGameDetailsById(gameId);
        JsonObjectBuilder objBuilder = Json.createObjectBuilder();

        objBuilder.add("game", gameDetails.toJSON());
        result = objBuilder.build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toString());
    }

    @GetMapping(path = "/comment")
    public ResponseEntity<String> searchComment(
            @RequestParam String q, @RequestParam Float score) {

        System.out.printf("q=%s, score=%f\n", q, score);
        List<Comment> results = bggSvc.searchComment(q, score, 20, 0);
        JsonArray result = null;
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        for (Comment g : results)
            arrBuilder.add(g.toJSON());
        result = arrBuilder.build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toString());
    }
}
