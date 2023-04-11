package paf.rev.pokemart.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import static paf.rev.pokemart.repository.DBqueries.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Repository("Inventory")
public class InventoryRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;
    
    @Autowired @Qualifier("Item")
    ItemRepo itemRepo;
    
    public List<String> getNoStock(){
        List<String> empty_invt = new ArrayList<>();
        SqlRowSet rs = jdbcTemplate.queryForRowSet(SELECT_NOSTOCK);
        while (rs.next()){
            empty_invt.add(rs.getString("item_id"));
        }
        return empty_invt;
    }

    public List<String> getStock(){
        List<String> stocked_invt = new ArrayList<>();
        SqlRowSet rs = jdbcTemplate.queryForRowSet(SELECT_STOCK);
        while (rs.next()){
            stocked_invt.add(rs.getString("item_id"));
        }
        return stocked_invt;
    }

    public List<String> getAllStock(){
        List<String> all_invt = new ArrayList<>();
        SqlRowSet rs = jdbcTemplate.queryForRowSet(SELECT_ALLSTOCK);
        while (rs.next()){
            all_invt.add(rs.getString("item_id"));
        }
        return all_invt;
    }

    public Optional<Integer> getStockLevel(int item_id){
        SqlRowSet rs = jdbcTemplate.queryForRowSet(SELECT_STOCK_BY_ITEM_ID,item_id);
        int stocklevel;
        if(rs.next()){
            stocklevel = rs.getInt("quantity");
            return Optional.of(stocklevel);
        }
        return Optional.empty();
    }

    public int upsertStockinDB(int item_id, int quantity){
        Optional<Integer> stocklevel = getStockLevel(item_id);
        if(stocklevel.isEmpty()){
            jdbcTemplate.update(INSERT_NEWSTOCK, item_id, quantity);
            return 1; //returns 1 if insert
        }
        jdbcTemplate.update(UPDATE_STOCK,quantity,item_id);
        return 0; //returns 0 if update
    }

    public int createStockfromDB(){
        List<String> all_items = itemRepo.getAllItemIds(9999, 0);
        int newstock = 0;
        Random rand = new Random();
        for(String item_id : all_items){
            System.out.println(item_id);
            if(getStockLevel(Integer.parseInt(item_id)).isEmpty()){
                jdbcTemplate.update(INSERT_NEWSTOCK, Integer.parseInt(item_id), rand.nextInt(99));
                newstock++;
            }
        }
        return newstock;
    }
    
}
