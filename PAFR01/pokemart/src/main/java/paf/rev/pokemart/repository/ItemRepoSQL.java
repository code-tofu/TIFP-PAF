package paf.rev.pokemart.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ItemRepoSQL {

    @Autowired
    JdbcTemplate jdbcTemplate;

    

}
