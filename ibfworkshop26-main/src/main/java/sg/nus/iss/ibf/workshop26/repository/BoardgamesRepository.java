package sg.nus.iss.ibf.workshop26.repository;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import sg.nus.iss.ibf.workshop26.model.Game;

@Repository
public class BoardgamesRepository {
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

}
