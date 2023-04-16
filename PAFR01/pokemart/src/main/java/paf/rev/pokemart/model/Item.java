package paf.rev.pokemart.model;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.JsonArray;
import jakarta.json.JsonObject;

public class Item {

    private final static String srcApi = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/";
    private final static String srcLocal = "src/main/resources/img/";

    private int item_id;
    private String name_id;
    private String name;
    private double cost;
    private String description;
    private String category;
    private String imgSrcApi;
    private String imgSrcLocal;

    // CONSTRUCTORS

    // JSON METHODS
    public static Item ItemFromJson(JsonObject itemJson){
        System.out.println(srcApi + srcLocal);
        Item item = new Item();
        item.setItem_id(itemJson.getInt("id"));
        item.setName_id(itemJson.getString("name"));
        item.setName(findJSONArrEn(itemJson.getJsonArray("names")).getString("name"));
        item.setCost(itemJson.getInt("cost")); //Java will auto convert int to double
        item.setDescription(findJSONArrEn(itemJson.getJsonArray("flavor_text_entries")).getString("text").replace("\n"," "));
        item.setCategory(itemJson.getJsonObject("category")
                                .getString("name"));
        item.setImgSrcApi(srcApi + item.getName_id() +".png");
        item.setImgSrcLocal(srcLocal + item.getName_id() +".png");
        // item.setImgSrcApi(itemJson.getJsonObject("sprites").getString("default"));
        // item.setImgSrcLocal(imgSrcAPI2Local(itemJson.getJsonObject("sprites").getString("default")));
        System.out.println(item);
        return item;
    }

    //SQL METHODS CREATE INSTEAD OF ROWMAPPER
    public static Item ItemFromRowset(SqlRowSet itemRowset){
        Item item = new Item();
        item.setItem_id(itemRowset.getInt("item_id"));
        item.setName_id(itemRowset.getString("name_id"));
        item.setName(itemRowset.getString("name"));
        item.setCost(itemRowset.getDouble("cost"));
        item.setDescription(itemRowset.getString("description"));
        item.setCategory(itemRowset.getString("category"));
        item.setImgSrcApi(srcApi + itemRowset.getString("name_id") +".png");
        item.setImgSrcLocal(srcLocal + itemRowset.getString("name_id") +".png");
        return item;        
    }

    public static String imgSrcAPI2Local(String srcStr){
        return srcStr.replace("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/","src/main/resources/img/");
    }

    //JSON METHODS
    public static JsonObject findJSONArrEn(JsonArray jsonArr){

        for(int i = 0;i<jsonArr.size();i++){
            JsonObject element = jsonArr.getJsonObject(i);
            if(element.getJsonObject("language").getString("name").equalsIgnoreCase("en")){
                return element;
            }
        }
        return jsonArr.getJsonObject(0); //default to 0
    }

    // GETTERS AND SETTERS
    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getName_id() {
        return name_id;
    }

    public void setName_id(String name_id) {
        this.name_id = name_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImgSrcApi() {
        return imgSrcApi;
    }

    public void setImgSrcApi(String imgSrcApi) {
        this.imgSrcApi = imgSrcApi;
    }

    public String getImgSrcLocal() {
        return imgSrcLocal;
    }

    public void setImgSrcLocal(String imgSrcLocal) {
        this.imgSrcLocal = imgSrcLocal;
    }

    // TOSTRING
    @Override
    public String toString() {
        return "Item [item_id=" + item_id + ", name_id=" + name_id + ", name=" + name + ", cost=" + cost
                + ", description=" + description + ", category=" + category + ", imgSrcApi=" + imgSrcApi
                + ", imgSrcLocal=" + imgSrcLocal + "]";
    }

    
//END OF CLASS
}


