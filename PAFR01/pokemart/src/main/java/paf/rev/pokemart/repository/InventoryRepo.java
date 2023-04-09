package paf.rev.pokemart.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class InventoryRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void restockInventory(int quantity){
        if(quantity == 0);
    }
    
}
