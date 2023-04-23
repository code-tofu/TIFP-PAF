package sg.edu.nus.iss.sqlmg.workshop.sqlmg.models;

import java.util.List;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class AggrRSVP {
    private String _id;
    private List<String> foodType;
    private Integer count;

    public List<String> getFoodType() {
        return foodType;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setFoodType(List<String> list) {
        this.foodType = list;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer ftCountl) {
        this.count = ftCountl;
    }

    public static AggrRSVP create(Document d) {
        AggrRSVP g = new AggrRSVP();
        g.set_id(d.getString("_id"));
        g.setFoodType(d.getList("foodType", String.class));
        g.setCount(d.getInteger("count"));
        return g;
    }

    public JsonObject toJSON() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("foodType", this.getFoodType().toString());
        builder.add("count", this.getCount());
        builder.add("_id", this.get_id());
        return builder.build();
    }

}
