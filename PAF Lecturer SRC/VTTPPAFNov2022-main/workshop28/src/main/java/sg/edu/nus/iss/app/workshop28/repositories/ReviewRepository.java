package sg.edu.nus.iss.app.workshop28.repositories;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AddFieldsOperation;
import org.springframework.data.mongodb.core.aggregation.AddFieldsOperation.AddFieldsOperationBuilder;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.app.workshop28.models.Comment;
import sg.edu.nus.iss.app.workshop28.models.EditedComment;
import sg.edu.nus.iss.app.workshop28.models.Game;
import sg.edu.nus.iss.app.workshop28.models.Review;

@Repository
public class ReviewRepository {

    private static final String REVIEWS_COL = "reviews";

    @Autowired
    private MongoTemplate mongoTemplate;

    public Review insertReview(Review r) {
        return mongoTemplate.insert(r, REVIEWS_COL);
    }

    public Review getReview(String _id) {
        ObjectId docId = new ObjectId(_id);
        return mongoTemplate.findById(docId, Review.class, REVIEWS_COL);
    }

    public Review updateEdits(String _id, EditedComment c) {
        ObjectId docId = new ObjectId(_id);
        Review r = mongoTemplate.findById(docId, Review.class, REVIEWS_COL);
        if (r != null) {
            EditedComment e = new EditedComment();
            e.setComment(c.getComment());
            e.setRating(c.getRating());
            e.setPosted(LocalDateTime.now());
            if (r.getEdited() != null) {
                r.getEdited().add(e);
            } else {
                List<EditedComment> ll = new LinkedList<EditedComment>();
                ll.add(e);
                r.setEdited(ll);
            }

            mongoTemplate.save(r, REVIEWS_COL);
        }
        return r;
    }

    /**
     * db.game.aggregate([ { $match: { gid: 3 } }, { $lookup: { from: "reviews",
     * localField: "gid", foreignField: "gameId", as: "reviewsDocs" } }, { $project:
     * { _id: 1, gid: 1, name: 1, year: 1, ranking: 1, users_rated: 1, url: 1,
     * image: 1, reviews: "$reviewsDocs._id", timestamp: "$$NOW" } }]);
     */
    public Optional<Game> aggregrateGameReviews(String gameId) {
        MatchOperation matchGameId = Aggregation.match(
                Criteria.where("gid").is(Integer.parseInt(gameId)));

        LookupOperation linkReviewsGame = Aggregation.lookup(REVIEWS_COL,
                "gid", "gameId", "reviewsDocs");

        ProjectionOperation projection = Aggregation
                .project("_id", "gid", "name", "year", "ranking",
                        "users_rated", "url", "image")
                .and("reviewsDocs._id").as("reviews");

        AddFieldsOperationBuilder adFieldOpsBld = Aggregation.addFields();
        adFieldOpsBld.addFieldWithValue("timestamp", LocalDateTime.now());
        AddFieldsOperation newFieldOps = adFieldOpsBld.build();

        Aggregation pipeline = Aggregation
                .newAggregation(matchGameId, linkReviewsGame, projection, newFieldOps);
        AggregationResults<Document> results = mongoTemplate
                .aggregate(pipeline, "game", Document.class);
        if (!results.iterator().hasNext())
            return Optional.empty();

        Document doc = results.iterator().next();
        Game g = Game.create(doc);
        return Optional.of(g);
    }

    public List<Comment> aggregateGamesComment(Integer limit, String username, Integer rating) {
        Criteria andCriteria = null;
        if (rating > 5) {
            andCriteria = new Criteria().andOperator(
                    Criteria.where("user").is(username),
                    Criteria.where("rating").gt(rating));
        } else {
            andCriteria = new Criteria().andOperator(
                    Criteria.where("user").is(username),
                    Criteria.where("rating").lt(rating));
        }

        MatchOperation matchUsernameOp = Aggregation.match(andCriteria);

        LookupOperation linkReviewsGame = Aggregation.lookup("game",
                "gid", "gid", "gameComment");

        ProjectionOperation projection = Aggregation
                .project("_id", "c_id", "user", "rating",
                        "c_text", "gid")
                .and("gameComment.name").as("game_name");

        LimitOperation limitRecords = Aggregation.limit(limit);

        Aggregation pipeline = Aggregation
                .newAggregation(matchUsernameOp,
                        linkReviewsGame, limitRecords, projection);
        AggregationResults<Comment> results = mongoTemplate
                .aggregate(pipeline, "comment", Comment.class);

        return (List<Comment>) results.getMappedResults();
    }

}
