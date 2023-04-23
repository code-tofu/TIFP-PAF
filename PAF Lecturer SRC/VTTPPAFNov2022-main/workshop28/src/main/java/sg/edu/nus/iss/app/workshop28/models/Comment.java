package sg.edu.nus.iss.app.workshop28.models;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Comment {
    private String _id;
    private String user;
    private Integer rating;
    private String c_text;
    private String c_id;
    private Integer gid;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getText() {
        return c_text;
    }

    public void setText(String text) {
        this.c_text = text;
    }

    @Override
    public String toString() {
        return "user: %s, rating: %d, text: %s".formatted(user, rating, c_text);
    }

    public static Comment create(Document d) {
        Comment c = new Comment();
        c.set_id(d.getObjectId("_id").toString());
        c.setC_id(d.getString("c_id"));
        c.setGid(d.getInteger("gid"));
        c.setUser(d.getString("user"));
        c.setRating(d.getInteger("rating"));
        c.setText(d.getString("c_text"));
        return c;
    }

    public JsonObject toJSON() {

        return Json.createObjectBuilder()
                .add("_id", get_id())
                .add("c_id", getC_id())
                .add("gid", getGid())
                .add("user", getUser())
                .add("rating", getRating())
                .add("c_text", getText())
                .build();
    }
}