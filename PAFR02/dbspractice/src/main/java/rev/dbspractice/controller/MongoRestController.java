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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import rev.dbspractice.model.MongoAccount;
import rev.dbspractice.model.MongoCustomer;
import rev.dbspractice.repository.MongoRepo;

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

}

// count
// sort i.e. aggregation and sort