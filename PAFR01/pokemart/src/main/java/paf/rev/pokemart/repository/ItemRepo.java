package paf.rev.pokemart.repository;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import paf.rev.pokemart.model.Item;

import static paf.rev.pokemart.repository.DBqueries.*;

@Repository("Item")
public class ItemRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Optional<Item> getItembyId(int item_id) {
        SqlRowSet rs = null;
        rs = jdbcTemplate.queryForRowSet(SELECT_ITEM_BY_ITEM_ID, new Object[] {item_id});
        if(rs.next()){
            return Optional.of(Item.ItemFromRowset(rs));
        }
        return Optional.empty();
    }

    public Optional<Double> getItemCostbyId(int item_id){
        SqlRowSet rs = jdbcTemplate.queryForRowSet(SELECT_COST_BY_ITEM_ID,item_id);
        double cost;
        if(rs.next()){
            cost = rs.getDouble("cost");
            return Optional.of(cost);
        }
        return Optional.empty();
    }
    
    //Using prepared statement
    public int createItem(Item item){
        Optional<Item> existingItem = getItembyId(item.getItem_id());
        if(existingItem.isPresent()) return 0;
        return jdbcTemplate.update(conn -> {
            PreparedStatement statement = conn.prepareStatement(INSERT_NEW_ITEM);
            statement.setInt(1,item.getItem_id());
            statement.setString(2,item.getName_id());
            statement.setString(3,item.getName());
            statement.setDouble(4,item.getCost());
            statement.setString(5,item.getDescription());
            statement.setString(6,item.getCategory());
            return statement;
        });
    }

    //Using a fixed SQL statemnt
    public List<String> getAllItemIds(int limit, int offset){
        List<String> item_ids = new ArrayList<>();
        //queryForRowSet(String sql, Object... args)
        SqlRowSet rs = jdbcTemplate.queryForRowSet(SELECT_ALL_ITEM_ID, limit, offset);
        // note that list is ordered
        while (rs.next()) {
            item_ids.add(rs.getString("item_id"));
        }
        return item_ids;
    }





}