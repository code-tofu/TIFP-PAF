package sg.edu.nus.iss.pafrsvp.model;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.joda.time.format.DateTimeFormat;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class RSVP implements Serializable {
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private DateTime confirmationDate;
    private String comments;
    // temp
    private Integer totalCnt;
    private String foodType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public DateTime getConfirmationDate() {
        return confirmationDate;
    }

    public void setConfirmationDate(DateTime confirmationDate) {
        this.confirmationDate = confirmationDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getTotalCnt() {
        return totalCnt;
    }

    public void setTotalCnt(Integer totalCnt) {
        this.totalCnt = totalCnt;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public static RSVP create(SqlRowSet rs){
        RSVP r  = new RSVP();
        r.setId(rs.getInt("id"));
        r.setName(rs.getString("name"));
        r.setEmail(rs.getString("email"));
        r.setPhone(rs.getString("phone"));
        DateTime cfmDate = new DateTime(DateTimeFormat
            .forPattern("dd/MM/yyyy")
            .parseDateTime(rs.getString("confirmation_date")));
        r.setConfirmationDate(cfmDate);
        r.setComments(rs.getString("comments"));
        r.setFoodType(rs.getString("food_type"));
        return r;
    }

    public static RSVP create(String jsonStr) throws Exception{
        JsonReader reader = Json.createReader(
            new ByteArrayInputStream(jsonStr.getBytes())  
        );
        return create(reader.readObject());
    }

    private static RSVP create(JsonObject readJObject){
        final RSVP rsvp=  new RSVP();
        rsvp.setName(readJObject.getString("name"));
        rsvp.setEmail(readJObject.getString("email"));
        rsvp.setPhone(readJObject.getString("phone"));
        rsvp.setConfirmationDate(
            new DateTime(Instant.parse(readJObject.getString("confirmation_date")))
        );
        rsvp.setComments(readJObject.getString("comments"));
        rsvp.setFoodType(readJObject.getString("food_type"));
        
        return rsvp;
    }

}
