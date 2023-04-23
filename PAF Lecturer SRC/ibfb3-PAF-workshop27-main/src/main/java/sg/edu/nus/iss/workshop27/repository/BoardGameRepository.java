package sg.edu.nus.iss.workshop27.repository;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.UpdateResult;

import sg.edu.nus.iss.workshop27.model.Game;
import sg.edu.nus.iss.workshop27.model.Review;

@Repository
public class BoardGameRepository {
    @Autowired
    MongoTemplate mongoTemplate;

    public List<Game> getAllGames(Integer limit, Integer offset) {
        Query query = new Query();
        Pageable pageable = PageRequest.of(offset, limit);
        query.with(pageable);
        return mongoTemplate.find(query, Document.class, "games").stream().map(d -> Game.create(d)).toList();

    }

    public List<Game> getSortedBoargGames(Integer limit, Integer offset) {
        Query query = new Query();
        Pageable pageable = PageRequest.of(offset, limit);
        query.with(pageable);
        query.with(Sort.by(Sort.Direction.ASC, "ranking"));
        return mongoTemplate.find(query, Document.class, "games").stream().map(d -> Game.create(d)).toList();
    }

    public Game getBoardGameById(Integer gameId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("gid").is(gameId));

        return mongoTemplate.findOne(query, Game.class, "games");
    }


    public Review getReviewById(String reviewId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("cid").is(reviewId));

        return mongoTemplate.findOne(query, Review.class, "reviews");
    }

    public Review insertComment(Review r){
        return mongoTemplate.insert(r, "reviews");    
    }

    public long updateComment(Review r){
        Query query = Query.query(
            Criteria.where("cid").is(r.getCid())
        );

        Update updateOps = new Update()
            .set("rating", r.getRating())
            .set("comment", r.getComment())
            .set("posted", r.getPosted())
            .set("edited", r.getEditedComment());

        
        UpdateResult result = mongoTemplate.updateMulti(query, updateOps, 
                Review.class, "reviews");
        return result.getModifiedCount(); 
    }

    
}
