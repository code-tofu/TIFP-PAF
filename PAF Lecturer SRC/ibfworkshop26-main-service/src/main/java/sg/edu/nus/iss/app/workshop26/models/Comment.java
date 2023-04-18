package sg.edu.nus.iss.app.workshop26.models;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Comment {
    private String user;
    private String text;
    private Integer rating;
    private Float score;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Comment [user= "
                .concat(this.user)
                .concat(", text=")
                .concat(this.text)
                .concat(", rating=")
                .concat("" + this.rating)
                .concat(", score=")
                .concat("" + this.score)
                .concat("]");
    }

    public static Comment create(Document d) {
        Comment c = new Comment();
        c.setUser(d.getString("user"));
        c.setText(d.getString("c_text"));
        c.setRating(d.getInteger("rating"));
        c.setScore(d.getDouble("score").floatValue());
        return c;
    }

    public JsonObject toJSON() {
        return Json.createObjectBuilder()
                .add("user", getUser())
                .add("c_text", getText())
                .add("rating", getRating())
                .add("score", getScore())
                .build();
    }

}
