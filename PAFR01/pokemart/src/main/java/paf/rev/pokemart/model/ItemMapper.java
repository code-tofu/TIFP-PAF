package paf.rev.pokemart.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ItemMapper implements RowMapper<Item>{

    // mapRow(ResultSet rs, int rowNum) Implementations must implement this method to map each row of data in the ResultSet.
    private final static String srcApi = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/";
    private final static String srcLocal = "src/main/resources/img/";
    
    @Override
    public Item mapRow(ResultSet itemRowset, int rowNum) throws SQLException {
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

    
}
