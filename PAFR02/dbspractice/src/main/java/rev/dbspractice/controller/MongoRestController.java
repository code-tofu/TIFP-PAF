package rev.dbspractice.controller;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.client.result.UpdateResult;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import rev.dbspractice.model.MongoAccount;
import rev.dbspractice.model.MongoCustomer;
import rev.dbspractice.model.MongoReview;
import rev.dbspractice.repository.MongoRepo;
import rev.dbspractice.util.Utils;

@RestController
@RequestMapping("/mongo")
public class MongoRestController {

    @Autowired
    MongoRepo mongoRepo;

    @GetMapping("/getAllAccounts")
    public ResponseEntity<String> getAllAccounts(@RequestParam(defaultValue = "20") String limit,
            @RequestParam(defaultValue = "0") String offset, @RequestParam(defaultValue = "1") String sort) {
        List<Document> queryList = mongoRepo.getAllAccounts(Integer.parseInt(limit), Integer.parseInt(offset),
                Integer.parseInt(sort));
        if (queryList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(
                    (Json.createObjectBuilder().add("404 NOT_FOUND", "No Results Found").build().toString()));
        }
        List<MongoAccount> accountList = new ArrayList<>();
        for (Document doc : queryList) {
            accountList.add(MongoAccount.mapAccount(doc));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(accountList.toString()); // need to code JSON Convertor
    }

    @GetMapping("/getAccountbyID/{id}")
    public ResponseEntity<String> getAccountbyID(@PathVariable String id) {
        Document doc = mongoRepo.getAccountbyID(Integer.parseInt(id));
        if (null == doc) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(
                    (Json.createObjectBuilder().add("404 NOT_FOUND", "Account Does Not Exist").build().toString()));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(MongoAccount.mapAccount(doc).toString()); // need to code JSON Convertor
    }

    @GetMapping("/getCustomerbyName")
    public ResponseEntity<String> getCustomerbyName(@RequestParam String username) {
        List<Document> queryList = mongoRepo.getCustomerbyName(username);
        if (queryList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(
                    (Json.createObjectBuilder().add("404 NOT_FOUND", "No Results Found").build().toString()));
        }
        List<MongoCustomer> customerList = new ArrayList<>();
        for (Document doc : queryList) {
            customerList.add(MongoCustomer.mapCustomer(doc, mongoRepo.getTierDetails(doc.getObjectId("_id"))));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(customerList.toString());
    }

    @GetMapping("/getCuisines")
    public ResponseEntity<String> getCuisines() {
        List<String> queryList = mongoRepo.getCuisines();
        if (queryList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(
                    (Json.createObjectBuilder().add("404 NOT_FOUND", "No Results Found").build().toString()));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(queryList.toString());
    }

    @GetMapping("/getCuisinesReplaced")
    public ResponseEntity<String> getCuisinesReplaced() {
        List<String> queryList = mongoRepo.getCuisinesReplaced();
        if (queryList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(
                    (Json.createObjectBuilder().add("404 NOT_FOUND", "No Results Found").build().toString()));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(queryList.toString());
    }

    @GetMapping("/getCuisinesByBorough/{boroughName}")
    public ResponseEntity<String> getCuisinesByBorough(@PathVariable String boroughName) {
        List<String> queryList = mongoRepo.getCuisinesByBorough(boroughName);
        if (queryList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(
                    (Json.createObjectBuilder().add("404 NOT_FOUND", "No Results Found").build().toString()));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(queryList.toString());
    }

    @GetMapping("/getGameByID/{id}")
    public ResponseEntity<String> getGameByID(@PathVariable String id) {
        Document doc = mongoRepo.getGameByID(Integer.parseInt(id));
        if (null == doc) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(
                    (Json.createObjectBuilder().add("404 NOT_FOUND", "Account Does Not Exist").build().toString()));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(doc.toString());
    }

    @PostMapping(path = "/postReview", consumes = "application/json")
    public ResponseEntity<String> postNewReview(@RequestBody String reviewPayload) {

        JsonObject reviewJson = Utils.getJSONObj(reviewPayload);
        Document doc = mongoRepo.insertReview(reviewJson);
        if (null == doc) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(
                    (Json.createObjectBuilder().add("404 NOT_FOUND", "Game Does Not Exist").build().toString()));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(doc.toString());
    }

    @PostMapping(path = "/postReviewClass", consumes = "application/json")
    public ResponseEntity<String> postNewReviewClass(@RequestBody String reviewPayload) {

        JsonObject reviewJson = Utils.getJSONObj(reviewPayload);
        MongoReview doc = mongoRepo.insertReviewClass(reviewJson);
        if (null == doc) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(
                    (Json.createObjectBuilder().add("404 NOT_FOUND", "Game Does Not Exist").build().toString()));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(doc.toString());
    }

    @PostMapping(path = "/updateReview/{id}", consumes = "application/json")
    public ResponseEntity<String> updateReview(@RequestBody String updatePayload, @PathVariable String id) {

        JsonObject updateJson = Utils.getJSONObj(updatePayload);
        UpdateResult updateRes = mongoRepo.updateReviewByID(id, updateJson);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(updateRes.toString());
    }

    @GetMapping("/review/{id}")
    public ResponseEntity<String> getReviewByID(@PathVariable String id) {
        Document existingReview = mongoRepo.findReviewByID(id);
        if (null == existingReview) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(
                    (Json.createObjectBuilder().add("404 NOT_FOUND", "Review Does Not Exist").build().toString()));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(MongoReview.jsonReviewFromDoc(existingReview, false).toString());
    }

    @GetMapping("/review/{id}/history")
    public ResponseEntity<String> getHistoryByID(@PathVariable String id) {
        Document existingReview = mongoRepo.findReviewByID(id);
        if (null == existingReview) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(
                    (Json.createObjectBuilder().add("404 NOT_FOUND", "Review Does Not Exist").build().toString()));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(MongoReview.jsonReviewFromDoc(existingReview, true).toString());
    }

    @GetMapping("/game/{id}/reviews")
    public ResponseEntity<String> getReviewsbyGame(@PathVariable String id) {
        Document doc = mongoRepo.getReviewsByGame(Integer.parseInt(id));
        if (null == doc) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(
                    (Json.createObjectBuilder().add("404 NOT_FOUND", "Account Does Not Exist").build().toString()));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(doc.toJson().toString());
    }

    @GetMapping("/games/{maxmin}")
    public ResponseEntity<String> getMaxMinReviewsbyGame(@PathVariable String maxmin,
            @RequestParam(defaultValue = "10") String limit) {
        List<Document> queryList = mongoRepo.getMaxMinReviews(maxmin, Integer.parseInt(limit));
        if (queryList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(
                    (Json.createObjectBuilder().add("404 NOT_FOUND", "No Results Found").build().toString()));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(queryList.toString());
    }

    @GetMapping("/games/sorted/{direction}")
    public ResponseEntity<String> getSortedGameList(@PathVariable String direction,
            @RequestParam(defaultValue = "20") String limit) {
        List<Document> queryList = mongoRepo.getSortedGames(direction, Integer.parseInt(limit));
        if (queryList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(
                    (Json.createObjectBuilder().add("404 NOT_FOUND", "No Results Found").build().toString()));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(queryList.toString());
    }
}
