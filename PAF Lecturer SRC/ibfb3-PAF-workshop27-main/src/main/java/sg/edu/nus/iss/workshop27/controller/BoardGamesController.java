package sg.edu.nus.iss.workshop27.controller;


import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import sg.edu.nus.iss.workshop27.exception.GameNotFoundException;
import sg.edu.nus.iss.workshop27.model.EditedComment;
import sg.edu.nus.iss.workshop27.model.Game;
import sg.edu.nus.iss.workshop27.model.Games;
import sg.edu.nus.iss.workshop27.model.Review;
import sg.edu.nus.iss.workshop27.service.BoardGameService;

@RestController
public class BoardGamesController {

    @Autowired
    private BoardGameService bgSvc;

    /*
     * to fetch board games
     */

    @GetMapping(path = "/games")
    public ResponseEntity<String> getAllBoardGames(@RequestParam Integer limit, @RequestParam Integer offset) {
        List<Game> lstGames = bgSvc.getAllGames(limit, offset);
        Games games = new Games();
        games.setGameList(lstGames);
        games.setOffset(offset);
        games.setLimit(limit);
        games.setTotal(lstGames.size());
        games.setTimeStamp(LocalDate.now());

        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

        objectBuilder.add("boardgames", games.toJson());
        JsonObject result = null;
        result = objectBuilder.build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toString());
    }

    /*
     * get boardgames in sorting order
     */

    @GetMapping("/games/rank")
    public ResponseEntity<String> getSortedBoargames(@RequestParam Integer limit, @RequestParam Integer offset) {
        List<Game> lstGames = bgSvc.getSortedBoargGames(limit, offset);

        Games games = new Games();
        games.setGameList(lstGames);
        games.setOffset(offset);
        games.setLimit(limit);
        games.setTotal(lstGames.size());
        games.setTimeStamp(LocalDate.now());

        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

        objectBuilder.add("boardgames", games.toJson());
        JsonObject result = null;
        result = objectBuilder.build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toString());
    }

    /*
     * get Boardgames by gameId
     */
    @GetMapping("/games/{gameId}")
    public ResponseEntity<String> getBoardGameById(@PathVariable Integer gameId) {
        Game game = bgSvc.getBoardGameById(gameId);

        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        objectBuilder.add("game", game.toJson());

        JsonObject result = objectBuilder.build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toString());
    }

    @PostMapping(path="/review", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity postReviewOnBoardGame(@ModelAttribute Review review){
        Review insertedReview = null;
        try {
            insertedReview = this.bgSvc.insertReview(review);
        } catch (GameNotFoundException e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body("");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(insertedReview.toJson().toString());

    }

    @PutMapping(path="/review/{reviewId}")
    public ResponseEntity updateExistingReview(@RequestBody EditedComment editedReview,
        @PathVariable String reviewId){
        editedReview.setCid(reviewId);
        long modifiedRecord = this.bgSvc.updateComment(editedReview);
        return ResponseEntity.ok(modifiedRecord);
    }

    @GetMapping(path="/review/{reviewId}")
    public ResponseEntity getCommentsWithRating(@PathVariable String reviewId){
        Review r = this.bgSvc.getReviewById(reviewId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(r.toJsonEdited().toString());
    }

    @GetMapping(path="/review/{reviewId}/history")
    public ResponseEntity getHistoricalCommentsWithRating(@PathVariable String reviewId){
        Review r = this.bgSvc.getReviewById(reviewId);
        System.out.println(r.getEditedComment().size());
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(r.toJsonEditedWithList().toString());
    }



}
