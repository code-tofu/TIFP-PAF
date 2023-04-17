package sg.edu.nus.iss.app.workshop26.repository;

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

import sg.edu.nus.iss.app.workshop26.models.Game;

@Repository
public class GameRepository {

        @Autowired
        private MongoTemplate mongoTemplate;

        public List<Game> search(Integer limit,
                        Integer offset) {

                final Pageable pageableRequest = PageRequest.of(offset, limit);
                Query query = new Query();
                query.with(pageableRequest);

                // lambda to find all the mongo game collections
                return mongoTemplate.find(query, Document.class, "games")
                                .stream()
                                .map(d -> Game.create(d))
                                .toList();
        }

        public List<Game> getGamesByRank() {
                Query query = new Query();
                query.with(Sort.by(Sort.Direction.ASC, "ranking"));
                // lambda to find all the mongo game collections sort by ranking
                return mongoTemplate.find(query, Document.class, "games")
                                .stream()
                                .map(d -> Game.create(d))
                                .toList();
        }

        public Game getGameDetailsById(Integer gid) {
                Query query = new Query();
                query.addCriteria(Criteria.where("gid").is(gid));
                return mongoTemplate.findOne(query,
                                Game.class,
                                "games");
        }
}
