package rev.dbspractice.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoExpression;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AddFieldsOperation;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationExpression;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ObjectOperators;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.aggregation.StringOperators;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.aggregation.AddFieldsOperation.AddFieldsOperationBuilder;
import org.springframework.data.mongodb.core.aggregation.StringOperators.Concat;
import org.springframework.data.mongodb.core.aggregation.StringOperators.ToLower;
import org.springframework.data.mongodb.core.aggregation.VariableOperators.Map;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;

import com.mongodb.client.result.UpdateResult;

import jakarta.json.JsonObject;
import rev.dbspractice.model.MongoReview;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;

@Repository
public class MongoRepo {

    @Autowired
    MongoTemplate mongoTemplate;

    public List<Document> getAllAccounts(int limit, int offset, int sort) {
        Query query = new Query();

        // note that if the result is to be sorted, then aggregation should be used
        if (sort == 1)
            query.limit(limit).skip(offset).with(Sort.by(Sort.Direction.ASC, "account_id"));
        if (sort == -1)
            query.limit(limit).skip(offset).with(Sort.by(Sort.Direction.DESC, "account_id"));

        return mongoTemplate.find(query, Document.class, "accounts");
    }

    public Document getAccountbyID(int id) {
        Query query = new Query(Criteria.where("account_id").is(id));
        return mongoTemplate.findOne(query, Document.class, "accounts");
    }

    public Document getTierDetails(ObjectId id) {
        MatchOperation matchObjectID = Aggregation.match(Criteria.where("_id").is(id));
        ProjectionOperation projectTierDetails = Aggregation.project().and(ObjectOperators.valueOf("tier_and_details")
                .toArray())
                .as("tierDetails");
        Aggregation pipeline = Aggregation.newAggregation(matchObjectID, projectTierDetails);

        AggregationResults<Document> result = mongoTemplate.aggregate(pipeline, "customers", Document.class);
        return result.getRawResults();
        // getMappedResults() returns List<Document> based on
        // AggregationResults<Document>
        // public class AggregationResults<T> implements Iterable<T>
    }

    public List<Document> getCustomerbyName(String username) {
        Query query = new Query(Criteria.where("username").regex(username, "i"));
        return mongoTemplate.find(query, Document.class, "customers");
    }

    public Document getGameByID(int gID) {
        Query query = new Query(Criteria.where("gid").is(gID));
        return mongoTemplate.findOne(query, Document.class, "game");
    }

    public Document insertReview(JsonObject reviewJson) {
        Document gameDoc = getGameByID(reviewJson.getInt("ID"));
        if (null == gameDoc) {
            return gameDoc;
        }
        Document reviewDoc = new Document();
        reviewDoc.append("user", reviewJson.getString("user"));
        reviewDoc.append("rating", reviewJson.getJsonNumber("rating").doubleValue());
        reviewDoc.append("comment", reviewJson.getString("comment"));
        reviewDoc.append("ID", reviewJson.getInt("ID"));
        reviewDoc.append("posted", LocalDate.now());
        reviewDoc.append("name", gameDoc.getString("name"));
        Document doc = mongoTemplate.insert(reviewDoc, "game_reviews");
        return doc;
    }

    public MongoReview insertReviewClass(JsonObject reviewJson) {
        Document gameDoc = getGameByID(reviewJson.getInt("gid"));
        if (null == gameDoc) {
            return null;
        }
        MongoReview newReview = new MongoReview();
        newReview.setComment(reviewJson.getString("comment"));
        newReview.setGamename(gameDoc.getString("name"));
        newReview.setGid(reviewJson.getInt("gid"));
        newReview.setPosted(LocalDate.now());
        newReview.setRating(reviewJson.getJsonNumber("rating").doubleValue());
        newReview.setUser(reviewJson.getString("name"));
        MongoReview doc = mongoTemplate.insert(newReview, "game_reviews");
        return doc;

    }

    public UpdateResult updateReviewByID(String reviewId, JsonObject updateJson) throws HttpClientErrorException {
        Update updateDef = new Update();
        Document existingReview = findReviewByID(reviewId);
        if (null == existingReview) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "NOT FOUND: REVIEW DOES NOT EXIST");
        }

        if (updateJson.containsKey("comment"))
            updateDef.set("comment", updateJson.getString("comment"));
        String existingComment = existingReview.getString("comment");
        double existingRating = existingReview.getDouble("rating");
        Date existingDate = existingReview.getDate("posted");
        Document existingHistory = new Document();
        existingHistory.append("comment", existingComment);
        existingHistory.append("rating", existingRating);
        existingHistory.append("posted", existingDate);
        updateDef.push("edited", existingHistory);
        try {
            double updateRating = updateJson.getJsonNumber("rating").doubleValue();
            updateDef.set("rating", updateRating);
            updateDef.set("posted", LocalDate.now());
            Document existingDoc = new Document();
            existingDoc.append("rating", existingRating);
            existingDoc.append("posted", existingDate);
            updateDef.push("edited", existingDoc);
            Query query = new Query(Criteria.where("_id").is(new ObjectId(reviewId)));
            return mongoTemplate.updateFirst(query, updateDef, "game_reviews");
        } catch (NullPointerException NPErr) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "BAD REQUEST: RATING NOT PROVIDED");
        }
    }

    public Document findReviewByID(String reviewId) {
        ObjectId reviewObjectId = new ObjectId(reviewId);
        return mongoTemplate.findById(reviewObjectId, Document.class, "game_reviews");
    }

    public List<String> getCuisines() {
        return mongoTemplate.findDistinct(new Query(), "cuisine", "restaurants", String.class);
    }

    public List<String> getCuisinesReplaced() {
        GroupOperation groupStage = Aggregation.group("null").addToSet("$cuisine").as("cuisines");
        UnwindOperation unwindStage = Aggregation.unwind("cuisines");
        ProjectionOperation replaceStage = Aggregation.project()
                .and(StringOperators.ReplaceAll.value("$cuisines").find("/").replacement("_")).as("cuisines");
        ProjectionOperation replaceSecondStage = Aggregation.project()
                .and(StringOperators.ReplaceAll.value("$cuisines").find(",").replacement(" ")).as("cuisines");
        ProjectionOperation projectStage = Aggregation.project("cuisines").and(ToLower.lower("$cuisines"))
                .as("cuisines_lower");
        SortOperation sortStage = Aggregation.sort(Sort.by(Direction.ASC, "cuisines_lower"));

        Aggregation pipeline = Aggregation.newAggregation(groupStage, unwindStage, replaceStage, replaceSecondStage,
                projectStage, sortStage);
        AggregationResults<Document> results = mongoTemplate.aggregate(pipeline, "restaurants", Document.class);
        List<Document> resultList = results.getMappedResults();
        List<String> cuisines = new ArrayList<>();
        for (Document doc : resultList) {
            cuisines.add(doc.getString("cuisines"));
        }
        return cuisines;

    }

    public List<String> getCuisinesByBorough(String boroughName) {
        return mongoTemplate.findDistinct(new Query(Criteria.where("borough").is(boroughName)), "cuisine",
                "restaurants", String.class);
    }

    public Document getReviewsByGame(int id) {
        MatchOperation matchStage = Aggregation.match(Criteria.where("gid").is(id));
        LookupOperation lookupStage = Aggregation.lookup("game_comment", "gid", "gid", "reviews");

        ProjectionOperation projectStage = Aggregation.project(
                "_id", "gid", "name", "year", "ranking", "users_rated", "url", "image")

                .and(
                        Map.itemsOf("reviews._id")
                                .as("review_id")
                                .andApply(
                                        Concat.stringValue("/review/").concatValueOf(
                                                AggregationExpression.from(
                                                        MongoExpression.create("""
                                                                $toString: "$$review_id"
                                                                """)))))
                .as("reviews");

        AddFieldsOperationBuilder addTimestamp = Aggregation.addFields();
        addTimestamp.addFieldWithValue("timestamp", LocalDateTime.now());
        AddFieldsOperation addTimestepStage = addTimestamp.build();

        Aggregation pipeline = Aggregation.newAggregation(matchStage, lookupStage, projectStage, addTimestepStage);
        AggregationResults<Document> docReviews = mongoTemplate.aggregate(pipeline, "game", Document.class);

        if (docReviews.iterator().hasNext())
            return docReviews.getRawResults();
        return null;
    }

    public List<Document> getMaxMinReviews(String maxmin, int limit) {

        SortOperation sortStage;
        GroupOperation groupStage;
        if (maxmin.equals("highest")) {
            sortStage = Aggregation.sort(Sort.by(Direction.DESC, "rating"));
            groupStage = Aggregation.group("gid")
                    .max("rating").as("max")
                    .first("$$ROOT").as("review");
        } else {
            sortStage = Aggregation.sort(Sort.by(Direction.ASC, "rating"));
            groupStage = Aggregation.group("gid")
                    .min("rating").as("min")
                    .first("$$ROOT").as("review");
        }
        LookupOperation lookupStage = Aggregation.lookup("game", "_id", "gid", "game");
        ProjectionOperation projectStage = Aggregation.project("$_id", "$game.name", "$max", "$review.user",
                "$review.c_text", "$review.c_id");
        AggregationOperation unwindStage = Aggregation.unwind("$name");
        LimitOperation limitStage = Aggregation.limit(limit);
        Aggregation pipeline = Aggregation.newAggregation(sortStage, groupStage, lookupStage, projectStage, unwindStage,
                limitStage);
        AggregationResults<Document> docReviews = mongoTemplate.aggregate(pipeline, "game_comment", Document.class);

        List<Document> gameReviews = new ArrayList<>();
        Iterator<Document> iter = docReviews.iterator();
        while (iter.hasNext()) {
            gameReviews.add(iter.next());
        }
        return gameReviews;
    }

    public List<Document> getSortedGames(String direction, int limit) {
        ProjectionOperation projectStage = Aggregation.project("name").and(ToLower.lower("$name")).as("name_lower");
        SortOperation sortStage = Aggregation.sort(Sort.by(Direction.ASC, "name_lower"));
        LimitOperation limitStage = Aggregation.limit(limit);
        if (direction.equals("desc"))
            sortStage = Aggregation.sort(Sort.by(Direction.DESC, "name_lower"));

        Aggregation pipeline = Aggregation.newAggregation(projectStage, sortStage, limitStage);
        AggregationResults<Document> results = mongoTemplate.aggregate(pipeline, "game", Document.class);
        List<Document> resultList = results.getMappedResults();
        return resultList;
    }

}

// count
// sort i.e. aggregation and sort
// insert
// updatemulti
// find and update