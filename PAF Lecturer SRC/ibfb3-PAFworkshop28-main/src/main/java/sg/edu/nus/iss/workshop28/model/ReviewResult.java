package sg.edu.nus.iss.workshop28.model;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

public class ReviewResult {
    private String rating;
    private String timestamp;
    private List<Review> games;
    public String getRating() {
        return rating;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }
    public String getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    
    public List<Review> getGames() {
        return games;
    }
    
    public void setGames(List<Review> games) {
        this.games = games;
    }

    // public static ReviewResult create(Document d){
    //     // TODO
    //     ReviewResult r = new ReviewResult();
    //     r.setRating(d.getString("rating"));
    //     return r;
    // }

    public JsonObject toJSON(){
        JsonArray  result = null;
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for(Review x : this.getGames())
            builder.add(x.toJSON());

        result = builder.build();
        return Json.createObjectBuilder()
                .add("rating", this.getRating())
                .add("games", result.toString())
                .add("timestamp", this.getTimestamp())
                .build();

    }


    

    
}
