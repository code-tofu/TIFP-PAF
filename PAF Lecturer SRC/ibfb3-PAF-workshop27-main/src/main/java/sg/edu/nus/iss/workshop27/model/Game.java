package sg.edu.nus.iss.workshop27.model;

import java.io.Serializable;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Game implements Serializable{
    private Integer gid;
    private String name;
    private Integer year;
    private Integer ranking;
    private Integer users_rated;

    public Game() {
    }

    public Game(Integer gid, String name, Integer year, Integer ranking, Integer users_rated, String url,
            String image) {
        this.gid = gid;
        this.name = name;
        this.year = year;
        this.ranking = ranking;
        this.users_rated = users_rated;
        this.url = url;
        this.image = image;
    }

    public Integer getUsers_rated() {
        return users_rated;
    }

    public void setUsers_rated(Integer users_rated) {
        this.users_rated = users_rated;
    }

    private String url;
    private String image;

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
        return "Game [gid=" + gid + ", name=" + name + ", year=" + year + ", ranking=" + ranking + ", users_rated="
                + users_rated + ", url=" + url + ", image=" + image + "]";
    }

    public static Game create(Document d) { 
        Game game = new Game();
        game.setGid(d.getInteger("gid"));
        game.setName(d.getString("name"));
        game.setYear(d.getInteger("year"));
        game.setRanking(d.getInteger("ranking"));
        game.setUsers_rated(d.getInteger("users_rated"));
        game.setUrl(d.getString("url"));
        game.setImage(d.getString("image"));
        return game;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
               .add("gid", getGid())
               .add("name", getName())
               .add("year", getYear())
               .add("ranking", getRanking())
               .add("users_rated", getUsers_rated())
               .add("url", getUrl())
               .add("image", getImage())
               .build(); 
    }

}