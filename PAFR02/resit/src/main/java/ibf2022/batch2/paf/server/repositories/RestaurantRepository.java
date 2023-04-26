package ibf2022.batch2.paf.server.repositories;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.StringOperators;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import ibf2022.batch2.paf.server.models.Comment;
import ibf2022.batch2.paf.server.models.Restaurant;

@Repository
public class RestaurantRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    // Task 2
    // db.restaurants.distinct("cuisine")
    public List<String> getCuisines() {
        return mongoTemplate.findDistinct(new Query(), "cuisine", "restaurants", String.class);
    }

    // Task 3
    // db.restaurants.find({cuisine:"American "},{_id:0,"name":1,"restaurant_id":1})
    public List<Restaurant> getRestaurantsByCuisine(String cuisine) {
        Query query = new Query(Criteria.where("cuisine").is(cuisine));
        query.fields().exclude("_id").include("name", "restaurant_id");
        List<Document> results = mongoTemplate.find(query, Document.class, "restaurants");
        List<Restaurant> restaurantList = new ArrayList<>();

        for (Document doc : results) {
            Restaurant newRest = new Restaurant();
            newRest.setName(doc.getString("name"));
            newRest.setRestaurantId(doc.getString("restaurant_id"));
            restaurantList.add(newRest);
        }
        restaurantList.sort(Comparator.comparing(Restaurant::getName));
        return restaurantList;
    }

    // Task 4
    /*
     * Write the MongoDB query for this method in the comments below
     * [
     * {
     * $match:
     * {
     * restaurant_id: "40356068",
     * //example using this restaurant id
     * },
     * },
     * {
     * $lookup:
     * {
     * from: "restaurants_comment",
     * localField: "restaurant_id",
     * foreignField: "restaurant_id",
     * as: "comments",
     * },
     * },
     * {
     * $project:
     * {
     * name: "$name",
     * restaurant_id: "$restaurant_id",
     * cuisine: {
     * $replaceAll: {
     * input: "$cuisine",
     * find: "/",
     * replacement: "_",
     * },
     * },
     * address: {
     * $concat: [
     * "$address.building",
     * ",",
     * "$address.street",
     * ",",
     * "$address.zipcode",
     * ",",
     * "$borough",
     * ],
     * },
     * comments: "$comments",
     * },
     * },
     * ]
     */
    public Optional<Restaurant> getRestaurantById(String id) {
        MatchOperation matchStage = Aggregation.match(Criteria.where("restaurant_id").is(id));
        LookupOperation lookupStage = Aggregation.lookup("restaurants_comment", "restaurant_id", "restaurant_id",
                "comments");
        ProjectionOperation projectStage = Aggregation.project("name", "restaurant_id", "cuisine")
                .and(StringOperators.Concat.valueOf("address.building")
                        .concat(",")
                        .concatValueOf("address.street")
                        .concat(",")
                        .concatValueOf("$address.zipcode")
                        .concat(",")
                        .concatValueOf("$borough"))
                .as("address")
                .andInclude("comments");

        Aggregation pipeline = Aggregation.newAggregation(matchStage, lookupStage, projectStage);
        AggregationResults<Document> results = mongoTemplate.aggregate(pipeline, "restaurants", Document.class);

        if (!results.iterator().hasNext())
            return Optional.empty();
        Document doc = results.iterator().next();
        Restaurant rest = new Restaurant();
        rest.setAddress(doc.getString("address"));
        rest.setCuisine(doc.getString("cuisine"));
        rest.setName(doc.getString("name"));
        rest.setRestaurantId(doc.getString("restaurant_id"));

        List<Document> docList = doc.getList("comments", Document.class);
        List<Comment> commentList = new ArrayList<>();
        for (Document docs : docList) {
            Comment newComment = new Comment();
            newComment.setComment(docs.getString("comment"));
            newComment.setDate(docs.getDate("date").getTime());
            newComment.setName(docs.getString("name"));
            newComment.setRating(docs.getInteger("rating"));
            newComment.setRestaurantId(docs.getString("restaurant_id"));
            commentList.add(newComment);
        }
        rest.setComments(commentList);
        System.out.println(rest);
        return Optional.of(rest);
    }

    // Task 5
    // Write the MongoDB query for this method in the comments below
    /*
     * db.restaurants_comment.insert(
     * ... {
     * ... "restaurant_id": "40356068",
     * ... "name" : "Zohan",
     * ... "date": new Date(),
     * ... "comment": "a fizzly bubbly time",
     * ... "rating": 4
     * ... })
     */
    public void insertRestaurantComment(Comment comment) {
        Document newDoc = new Document();
        newDoc.append("restaurant_id", comment.getRestaurantId());
        newDoc.append("name", comment.getName());
        newDoc.append("date", new Date(comment.getDate()));
        newDoc.append("comment", comment.getComment());
        newDoc.append("rating", comment.getRating());
        Document doc = mongoTemplate.insert(newDoc, "restaurants_comment");
        System.out.println(doc.toJson().toString());
    }
}
