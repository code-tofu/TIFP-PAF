package sg.edu.nus.iss.workshop27.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.workshop27.exception.GameNotFoundException;
import sg.edu.nus.iss.workshop27.model.EditedComment;
import sg.edu.nus.iss.workshop27.model.Game;
import sg.edu.nus.iss.workshop27.model.Review;
import sg.edu.nus.iss.workshop27.repository.BoardGameRepository;

@Service
public class BoardGameService {
    
    @Autowired
    private BoardGameRepository repo;
    

    public List<Game> getAllGames(Integer limit, Integer offset){
        return this.repo.getAllGames(limit, offset);
    }

    public Game getBoardGameById(Integer gameId) {
        return this.repo.getBoardGameById(gameId);
    }


    public List<Game> getSortedBoargGames(Integer limit, Integer offset) {
        return this.repo.getSortedBoargGames(limit, offset);
    }

    public Review insertReview(Review r) throws GameNotFoundException{
        Game g = this.getBoardGameById(r.getGid());
        if(g == null){
            System.out.println("game not found !");
            throw new GameNotFoundException("Game not found");
        }
        r.setName(g.getName());
        r.setPosted(LocalDateTime.now());
        return this.repo.insertComment(r);
    }

    public long updateComment(EditedComment r){ 
        Review result = this.repo.getReviewById(r.getCid());
        List<EditedComment> ll = result.getEditedComment();
        System.out.println("r comment : " + r.getComment());
        System.out.println("result comment :" + result.getComment());
        if(result.getEditedComment() == null){
            ll = new ArrayList<>();
            result.setEditedComment(ll);
        }
        EditedComment e = new EditedComment();
        e.setComment(result.getComment());
        e.setRating(result.getRating());
        e.setPosted(result.getPosted());
        result.getEditedComment().add(e);
        
        result.setComment(r.getComment());
        result.setRating(r.getRating());
        result.setPosted(LocalDateTime.now());
        return this.repo.updateComment(result);
    }

    public Review getReviewById(String reviewId) {
        return this.repo.getReviewById(reviewId);
    }

}
