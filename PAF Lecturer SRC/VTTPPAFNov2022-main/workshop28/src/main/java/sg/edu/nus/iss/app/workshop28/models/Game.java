package sg.edu.nus.iss.app.workshop28.models;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

import org.bson.Document;
import org.bson.types.ObjectId;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

public class Game {
    private Integer gid;
    private String name;
    private Integer year;
    private Integer ranking;
    private Integer users_rated;
    private String url;
    private String image;
    private String timestamp;
    private String[] reviews;

    public String[] getReviews() {
        return reviews;
    }

    public void setReviews(String[] reviews) {
        this.reviews = reviews;
    }

    public Integer getUsers_rated() {
        return users_rated;
    }

    public void setUsers_rated(Integer users_rated) {
        this.users_rated = users_rated;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public Integer getUsersRated() {
        return users_rated;
    }

    public void setUsersRated(Integer usersRated) {
        this.users_rated = usersRated;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Game [gid= "
                .concat("" + this.gid)
                .concat(", name=")
                .concat(this.name)
                .concat(", year=")
                .concat("" + this.year)
                .concat(", ranking=")
                .concat("" + this.ranking)
                .concat(", usersRated=")
                .concat("" + this.users_rated)
                .concat(", url=")
                .concat(this.url)
                .concat(", image=")
                .concat(this.image)
                .concat("]");
    }

    public static Game create(Document d) {
        Game g = new Game();
        List reviewsIdArr = (ArrayList) d.get("reviews");
        List newReviewsId = new LinkedList<>();
        for (Object a : reviewsIdArr) {
            ObjectId oa = (ObjectId) a;
            newReviewsId.add("/review/" + oa.toString());
        }

        g.setGid(d.getInteger("gid"));
        g.setName(d.getString("name"));
        g.setYear(d.getInteger("year"));
        g.setRanking(d.getInteger("ranking"));
        g.setUsersRated(d.getInteger("users_rated"));
        g.setUrl(d.getString("url"));
        g.setImage(d.getString("image"));
        g.setTimestamp(d.getDate("timestamp").toString());
        g.setReviews((String[]) newReviewsId.toArray(new String[newReviewsId.size()]));
        return g;
    }

    public JsonObject toJSON() {
        JsonArray reviewsJ = null;
        JsonArrayBuilder bld = Json.createArrayBuilder();
        for (String x : getReviews())
            bld.add(x);
        reviewsJ = bld.build();
        return Json.createObjectBuilder()
                .add("gid", getGid())
                .add("name", getName())
                .add("year", getYear())
                .add("ranking", getRanking())
                .add("users_rated", getUsersRated() != null ? getUsersRated() : 0)
                .add("url", getUrl())
                .add("image", getImage())
                .add("timestamp", getTimestamp())
                .add("reviews", reviewsJ.toString())
                .build();
    }

}