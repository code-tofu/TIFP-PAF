package paf.rev.pokemart.repository;

import static paf.rev.pokemart.repository.DBqueries.*;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerRepo {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public Optional<String> getPassword(String email){
        SqlRowSet rs = jdbcTemplate.queryForRowSet(SELECT_PASSWORD_BY_EMAIL,email);
        if(rs.next()){
            return Optional.of(rs.getString("password"));
        }
        return Optional.empty();
    }
}