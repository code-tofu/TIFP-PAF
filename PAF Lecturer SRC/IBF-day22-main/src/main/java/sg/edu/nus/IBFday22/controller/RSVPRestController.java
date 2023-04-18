package sg.edu.nus.IBFday22.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Build;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import sg.edu.nus.IBFday22.model.RSVP;
import sg.edu.nus.IBFday22.repository.RSVPRepository;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class RSVPRestController {
    RSVPRepository repository;

    RSVPRestController(RSVPRepository repository) {
        this.repository = repository;
    }

    /*
     * Fetchh all rsvps
     * 
     */
    @GetMapping("/rsvps")
    public ResponseEntity<String> getAllRsvps() {
        List<RSVP> rsvps = repository.getAllRSVP();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (RSVP r : rsvps) {
            arrayBuilder.add(r.toJson());
            }
        JsonArray result = arrayBuilder.build();

        
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toString());
    }

    @GetMapping("/rsvp")
    public ResponseEntity<String> getRSVPByName(@RequestParam String name){
        List<RSVP> rsvp = repository.getRSVPByName(name);

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (RSVP r : rsvp) {
            arrayBuilder.add(r.toJson());
            }
        JsonArray result = arrayBuilder.build();

        if(rsvp.isEmpty())
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body("{'error_code' : " + HttpStatus.NOT_FOUND + "'}");
    

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toString());
    

    }

 //for content type Content-Type: application/x-www-form-urlencoded, use consumes = "application/x-www-form-urlencoded") or MediaType.APPLICATION_FORM_URLENCODED_VALUE and in parameter, use HttpServletRequest instead of RequestBody and get field values from HttpServletRequest object
    @PostMapping(path = "/rsvp", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> insertUpdateRSVP(@RequestBody String json){
        RSVP rsvp = null;
        JsonObject jsonObject = null;
        try{
        rsvp = RSVP.create(json); //converting json to java object
        }catch(Exception e){
         e.printStackTrace();
        jsonObject = Json.createObjectBuilder().add("error", e.getMessage()).build();
        return ResponseEntity.badRequest().body(jsonObject.toString());

        }
        RSVP result = repository.createRsvp(rsvp);

         jsonObject = Json.createObjectBuilder()
        .add("rsvpID", result.getId())
        .build();
 
        return ResponseEntity.status(HttpStatus.CREATED)
        .contentType(MediaType.APPLICATION_JSON)
        .body(jsonObject.toString());
    }

     //for content type Content-Type: application/x-www-form-urlencoded, use consumes = "application/x-www-form-urlencoded") or MediaType.APPLICATION_FORM_URLENCODED_VALUE and in parameter, use HttpServletRequest instead of RequestBody and get field values from HttpServletRequest object
    @PutMapping(path = "/rsvp", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> putRSVP(@RequestBody String json){
        RSVP rsvp = null;
        boolean rsvpResult = false;
        JsonObject resp;
        try{
            rsvp = RSVP.create(json);
        }catch(Exception e){
            e.printStackTrace();
            resp = Json.createObjectBuilder()
            .add("error :", e.getMessage())
            .build();
            return ResponseEntity.badRequest().body(resp.toString());
        }

        rsvpResult = repository.updateRSVP(rsvp);
        resp = Json.createObjectBuilder()
        .add("updated", rsvpResult)
        .build();

        return ResponseEntity
        .status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(resp.toString());
    }

    @GetMapping(path = "/rsvps/count")
    public ResponseEntity<String> getTotalRSVPCounts(){

        JsonObject resp;

        Long total_rsvps = repository.getTotalRSVPCount();

        resp = Json.createObjectBuilder()
        .add("total_count", total_rsvps)
        .build();

        return ResponseEntity
        .status(HttpStatus.CREATED)
        .contentType(MediaType.APPLICATION_JSON)
        .body(resp.toString()); 

    }

}
