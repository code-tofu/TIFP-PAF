package rev.dbspractice.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class MongoReview {

    private String user;
    private double rating;
    private String comment;
    private int gid;
    private LocalDate posted;
    private String gamename;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public LocalDate getPosted() {
        return posted;
    }

    public void setPosted(LocalDate posted) {
        this.posted = posted;
    }

    public String getGamename() {
        return gamename;
    }

    public void setGamename(String gamename) {
        this.gamename = gamename;
    }

    @Override
    public String toString() {
        return "MongoReview [user=" + user + ", rating=" + rating + ", comment=" + comment + ", gid=" + gid
                + ", posted=" + posted + ", gamename=" + gamename + "]";
    }

    public static JsonObject jsonReviewFromDoc(Document doc,boolean history){
        JsonObjectBuilder reviewJson = Json.createObjectBuilder()
                                .add("user", doc.getString("user"))
                                .add("rating", doc.getDouble("rating"))
                                .add("comment", doc.getString("comment"))
                                .add("ID",doc.getInteger("ID"))
                                .add("posted",doc.getDate("posted").toString())
                                .add("name",doc.getString("name"));
        if(history){ //there  has to be a better way to do this
            List<Document> historyList = doc.getList("edited",Document.class);
            JsonArrayBuilder historyArr = Json.createArrayBuilder();
            for (Document edited:historyList){
                if(edited.containsKey("comment")){
                    historyArr.add(
                        Json.createObjectBuilder().add("comment",edited.getString("comment"))
                                                    .add("rating", edited.getDouble("rating"))
                                                    .add("posted", edited.getDate("posted").toString()).build()
                    );
                }else{
                    historyArr.add(
                        Json.createObjectBuilder().add("rating", edited.getDouble("rating"))
                                                    .add("posted", edited.getDate("posted").toString()).build()
                        );
                }
            }
            return reviewJson.add("edited",historyArr.build())
                .add("timestamp",LocalDateTime.now().toString())
                .build();
        } else {
            return reviewJson.add("edited",doc.containsKey("edited"))
                                .add("timestamp",LocalDateTime.now().toString())
                                .build();
        }
    }
}


// user: <name form field>,
// rating: <rating form field>,
// comment: <comment form field>,
// ID: <game id form field>,
// posted: <date>,
// name: <The board game’s name as per ID>
