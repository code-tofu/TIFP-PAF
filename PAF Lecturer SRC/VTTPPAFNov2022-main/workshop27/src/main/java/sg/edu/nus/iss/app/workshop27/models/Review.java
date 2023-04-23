package sg.edu.nus.iss.app.workshop27.models;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class Review {
    private String _id;
    private String user;
    private Integer rating;
    private String comment;
    private Integer gameId;
    private LocalDateTime posted;
    private String boardGame;
    private List<EditedComment> edited;
    private Boolean isEdited;
    private LocalDateTime timestamp;

    public Review() {
    }

    public Review(String user, Integer rating,
            String comment, Integer gameId,
            String boardGame) {
        this.user = user;
        this.rating = rating;
        this.comment = comment;
        this.gameId = gameId;
        this.boardGame = boardGame;
        this.posted = LocalDateTime.now();
    }

    public Boolean getIsEdited() {
        return isEdited;
    }

    public void setIsEdited(Boolean isEdited) {
        this.isEdited = isEdited;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<EditedComment> getEdited() {
        return edited;
    }

    public void setEdited(List<EditedComment> edited) {
        this.edited = edited;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public LocalDateTime getPosted() {
        return posted;
    }

    public void setPosted(LocalDateTime posted) {
        this.posted = posted;
    }

    public String getBoardGame() {
        return boardGame;
    }

    public void setBoardGame(String boardGame) {
        this.boardGame = boardGame;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public static Review create(Document d) {
        Review g = new Review();
        g.set_id(d.getObjectId("_id").toString());
        g.setUser(d.getString("user"));
        g.setRating(d.getInteger("rating"));
        g.setComment(d.getString("comment"));
        g.setGameId(d.getInteger("gameId"));
        LocalDateTime postedDt = Instant.ofEpochMilli(d.getDate("posted").getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        g.setPosted(postedDt);
        g.setBoardGame(d.getString("name"));

        return g;
    }

    public JsonObject toJSON(boolean switchProp) {
        List<JsonObject> js = this.getEdited()
                .stream()
                .map(c -> c.toJSON())
                .toList();
        JsonObjectBuilder builder = Json.createObjectBuilder();

        builder.add("_id", this.get_id());
        builder.add("user", this.getUser());
        builder.add("rating", getRating());
        builder.add("comment", getComment());
        builder.add("gameId", getGameId());
        builder.add("posted", getPosted().toString());
        builder.add("boardGame", getBoardGame());
        if (switchProp) {
            if (getIsEdited() != null)
                builder.add("edited", getIsEdited());
        } else {
            builder.add("edited", js.toString());
        }

        return builder.build();
    }

}
