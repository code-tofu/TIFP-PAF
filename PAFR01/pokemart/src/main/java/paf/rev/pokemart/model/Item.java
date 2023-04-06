package paf.rev.pokemart.model;



import org.springframework.stereotype.Component;

import jakarta.json.JsonArray;
import jakarta.json.JsonObject;


//JsonValue includes JsonObject
import jakarta.json.JsonValue;

@Component
public class Item {

    private int item_id;
    private String name_id;
    private String name;
    private double cost;
    private String description;
    private String category;
    private String spriteSRCapi;
    private String spriteSRClocal;

    // CONSTRUCTORS


    // JSON METHODS
    public static Item ItemFromJson(JsonObject itemJson){
        Item item = new Item();
        item.setItem_id(itemJson.getInt("id"));
        item.setName_id(itemJson.getString("name"));
        item.setName(findJSONArrEn(itemJson.getJsonArray("names")).getString("name"));
        item.setCost(itemJson.getInt("cost")); //Java will auto convert int to double
        item.setDescription(findJSONArrEn(itemJson.getJsonArray("flavor_text_entries")).getString("text").replace("\n"," "));
        item.setCategory(itemJson.getJsonObject("category")
                                .getString("name"));
        item.setSpriteSRCapi(itemJson.getJsonObject("sprites")
                                    .getString("default"));
        item.setSpriteSRClocal(imgSrcAPI2Local(itemJson.getJsonObject("sprites")
        .getString("default")));
        System.out.println(item);
        return item;
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

    public String getSpriteSRCapi() {
        return spriteSRCapi;
    }

    public void setSpriteSRCapi(String spriteSRCapi) {
        this.spriteSRCapi = spriteSRCapi;
    }

    public String getSpriteSRClocal() {
        return spriteSRClocal;
    }

    public void setSpriteSRClocal(String spriteSRClocal) {
        this.spriteSRClocal = spriteSRClocal;
    }

    
    // TOSTRING
    @Override
    public String toString() {
        return "Item [item_id=" + item_id + ", name_id=" + name_id + ", name=" + name + ", cost=" + cost
                + ", description=" + description + ", category=" + category + ", spriteSRCapi=" + spriteSRCapi
                + ", spriteSRClocal=" + spriteSRClocal + "]";
    }


    public static String imgSrcAPI2Local(String srcStr){
        return srcStr.replace("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/","src/main/resources/img/");
    }

    public static JsonObject findJSONArrEn(JsonArray jsonArr){

        for(int i = 0;i<jsonArr.size();i++){
            JsonObject element = jsonArr.getJsonObject(i);
            if(element.getJsonObject("language").getString("name").equalsIgnoreCase("en")){
                return element;
            }
        }
        return jsonArr.getJsonObject(0); //default to 0
    }

//END OF CLASS
}


/* {
    "cost": 0,
    "category": {
    "name": "standard-balls",
    }
    "flavor_text_entries": [
            {
                "language": {
                    "name": "en",
                    "url": "https://pokeapi.co/api/v2/language/9/"
                },
                "text": "The best BALL that\ncatches a POKéMON\nwithout fail.",
                "version_group": {
                    "name": "ruby-sapphire",
                    "url": "https://pokeapi.co/api/v2/version-group/5/"
                }
    }, ...
    ],
    "id": 1,
    "name": "master-ball",
    "names": [
    {
        "language": {
            "name": "en",
            "url": "https://pokeapi.co/api/v2/language/9/"
        },
        "name": "Master Ball"
    },...
    ],
    "sprites": {
    "default": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/master-ball.png"
    }

} */