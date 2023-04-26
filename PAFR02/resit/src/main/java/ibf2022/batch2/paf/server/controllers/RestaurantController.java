package ibf2022.batch2.paf.server.controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ibf2022.batch2.paf.server.models.Comment;
import ibf2022.batch2.paf.server.models.Restaurant;
import ibf2022.batch2.paf.server.services.RestaurantService;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;

@RestController
public class RestaurantController {

    @Autowired
    RestaurantService restaurantSvc;

    // Task 2 - request handler
    @GetMapping("/api/cuisines")
    public ResponseEntity<String> getCuisines() {
        List<String> cuisineList = restaurantSvc.getCuisines();
        JsonArrayBuilder jsonAB = Json.createArrayBuilder(cuisineList);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonAB.build().toString());
    }

    // Task 3 - request handler
    @GetMapping("/api/restaurants/{cuisine}")
    // when path variable is read back to String by spring, urlencoding is removed.
    public ResponseEntity<String> getRestaurantsbyCuisine(@PathVariable String cuisine) {

        List<Restaurant> resturantList = restaurantSvc.getRestaurantsByCuisine(cuisine.replace("_", "/"));
        JsonArrayBuilder jsonAB = Json.createArrayBuilder();
        for (Restaurant rest : resturantList) {
            JsonObjectBuilder jsonOB = Json.createObjectBuilder()
                    .add("restaurantId", rest.getRestaurantId())
                    .add("name", rest.getName());
            System.out.println(rest.getRestaurantId());
            jsonAB.add(jsonOB);
        }

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonAB.build().toString());

    }

    // Task 4 - request handler
    @GetMapping("/api/restaurant/{restaurant_id}")
    public ResponseEntity<String> getRestaurantbyId(@PathVariable String restaurant_id) {
        Optional<Restaurant> result = restaurantSvc.getRestaurantById(restaurant_id);
        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(
                    (Json.createObjectBuilder().add("error", "Missing " + restaurant_id).build().toString()));
        }
        Restaurant rest = result.get();
        JsonObjectBuilder jsonOB = Json.createObjectBuilder()
                .add("restaurant_id", rest.getRestaurantId())
                .add("name", rest.getName())
                .add("cuisine", rest.getCuisine())
                .add("address", rest.getAddress());
        JsonArrayBuilder jsonAB = Json.createArrayBuilder();
        for (Comment comments : rest.getComments()) {
            JsonObjectBuilder jsonArrOB = Json.createObjectBuilder()
                    .add("restaurant_id", comments.getRestaurantId())
                    .add("name", comments.getName())
                    .add("date", comments.getDate())
                    .add("comment", comments.getComment())
                    .add("rating", comments.getRating());
            jsonAB.add(jsonArrOB);
        }
        jsonOB.add("comments", jsonAB);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonOB.build().toString());
    }

    // name=Phantom&rating=1&comment=bad!%20better%20to%20eat%20at%20Phantom%20Muchentuchen&restaurantId=40356068
    // Task 5 - request handler
    @PostMapping("/api/restaurant/comment")
    public ResponseEntity<String> postRestaurantComment(@RequestBody MultiValueMap<String, String> post) {
        Comment newComment = new Comment();
        newComment.setComment(post.getFirst("comment"));
        newComment.setName(post.getFirst("name"));
        newComment.setRating(Integer.parseInt(post.getFirst("rating")));
        newComment.setRestaurantId(post.getFirst("restaurantId"));
        Date d = new Date();
        long e = d.getTime();
        newComment.setDate(e);
        restaurantSvc.postRestaurantComment(newComment);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body("");
    }

}
