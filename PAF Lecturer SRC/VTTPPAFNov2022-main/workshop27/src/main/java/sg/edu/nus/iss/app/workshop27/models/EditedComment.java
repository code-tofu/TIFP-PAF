package sg.edu.nus.iss.app.workshop27.models;

import java.time.LocalDateTime;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class EditedComment {

    private String comment;
    private Integer rating;
    private LocalDateTime posted;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public LocalDateTime getPosted() {
        return posted;
    }

    public void setPosted(LocalDateTime posted) {
        this.posted = posted;
    }

    public JsonObject toJSON() {
        return Json.createObjectBuilder()
                .add("comment", this.getComment())
                .add("rating", getRating())
                .add("posted", getPosted().toString())
                .build();
    }
}
