package sg.edu.nus.iss.workshop28.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AddFieldsOperation;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.AddFieldsOperation.AddFieldsOperationBuilder;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.workshop28.model.Game;
import sg.edu.nus.iss.workshop28.model.Review;

@Repository
public class BoardGameRepository {
    

    @Autowired
    private MongoTemplate template;

    /**
     * db.games.aggregate([
            {
                $match: {gid:3}
            },
            {
                $lookup: 
                {
                    from: "reviews",
                    localField: "gid",
                    foreignField: "gid",
                    as: "reviewsDocs"
                }
            },
            {
                $project: { _id: -1, gid: 1,
                    name: 1, year: 1., ranking: 1, users_rated: 1, url:1, image:1, 
                    reviews: "$reviewsDocs._id", timestamp: "$$NOW"
                }
            },
        ]);
    *
     **/
    public Optional<Game> aggregrateGameReviews(String gameId){
        // match against the game id --> games collection
        MatchOperation matchGameId = Aggregation.match(
            Criteria.where("gid").is(Integer.parseInt(gameId))
        );

        // lookup is used to establish relationship reviews <--> games col
        LookupOperation lk = Aggregation.lookup("reviews", 
                "gid", "gid", "reviewsDocs");

        ProjectionOperation pOp= Aggregation.project("_id", "gid", "name", "year", 
            "ranking", "users_rated", "url", "image")
            .and("reviewsDocs._id").as("reviews");

        AddFieldsOperationBuilder a = Aggregation.addFields();
        a.addFieldWithValue("timestamp", LocalDateTime.now());
        AddFieldsOperation newFieldOp = a.build();
        
        Aggregation pipeline = Aggregation.newAggregation(matchGameId, lk, pOp, newFieldOp);

        AggregationResults<Document> r = template.aggregate(pipeline, "games", 
                    Document.class);

        if(!r.iterator().hasNext())
            return Optional.empty();
        
        Document doc = r.iterator().next();
        Game g = Game.create(doc);
        return Optional.of(g);
    }

    /**
     * db.reviews.aggregate([
            {
                $match: {"$and": [{"user": "desertfox2004"}, {"rating": {"$gt": 5}}]}
            },
            {
                $lookup: {"from": "games", "localField": "gid", 
                        "foreignField": "gid", "as": "gameReviews"}
            },
            {
                $project: { "_id": 1, "c_id": 1, "user": 1, "rating": 1, "c_text": 1, "gid": 1,
                    "game_name": "$gameReviews.name"}
            },
            { "$limit": 500}
        ]);
     */
    public List<Review> aggregatesMinMaxGameReviews(Integer limit, 
                String username, String rating){
        Criteria c = null;
        if(rating.equals("highest")){
            c = new Criteria().andOperator(
                Criteria.where("user").is(username),
                Criteria.where("rating").gte(5)
            );
        }else{
            c = new Criteria().andOperator(
                Criteria.where("user").is(username),
                Criteria.where("rating").lte(4)
            );
        }

        MatchOperation mOp = Aggregation.match(c);

        LookupOperation lOp= Aggregation.lookup("games", 
                "gid", "gid", "gameComment");

        ProjectionOperation pop = Aggregation.project(
            "_id", "c_id", "user", "rating", "c_text", "gid"
        ).and("gameComment.name").as("game_name");

        LimitOperation limitOp = Aggregation.limit(limit);

        Aggregation pipeline = Aggregation.newAggregation(
            mOp, lOp, limitOp, pop 
        );

        AggregationResults<Review> r = template.aggregate(pipeline, 
                    "reviews", 
                    Review.class);

        return (List<Review>) r.getMappedResults();
    }
}
