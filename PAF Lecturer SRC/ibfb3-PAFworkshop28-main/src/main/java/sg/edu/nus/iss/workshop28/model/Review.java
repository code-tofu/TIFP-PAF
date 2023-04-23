package sg.edu.nus.iss.workshop28.model;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Review {
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
    public String getC_text() {
        return c_text;
    }
    public void setC_text(String c_text) {
        this.c_text = c_text;
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

    public static Review create(Document d){
        Review r = new Review();
        r.set_id(d.getObjectId("_id").toString());
        r.setC_id(d.getString("c_id"));
        r.setGid(d.getInteger("gid"));
        r.setUser(d.getString("user"));
        r.setRating(d.getInteger("rating"));
        r.setC_text(d.getString("c_text"));
        return r;
    }
    
    public JsonObject toJSON(){
        return Json.createObjectBuilder()
                .add("_id", get_id())
                .add("c_id", getC_id())
                .add("gid", getGid())
                .add("user", getUser())
                .add("rating", getRating())
                .add("c_text", getC_text())
                .build();
    }
  
}
