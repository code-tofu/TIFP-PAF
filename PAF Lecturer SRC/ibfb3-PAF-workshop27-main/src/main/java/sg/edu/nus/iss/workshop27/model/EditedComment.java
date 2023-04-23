package sg.edu.nus.iss.workshop27.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;

public class EditedComment implements Serializable{
    private Integer rating;
    private String comment;
    private LocalDateTime posted;
    private String cid;
    
    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getPosted() {
        return posted;
    }

    public void setPosted(LocalDateTime posted) {
        this.posted = posted;
    }

    public JsonObjectBuilder toJSON() {
        return Json.createObjectBuilder()
                .add("c_text", getComment())
                .add("rating", getRating())
                .add("posted", getPosted().toString());
    }
}
