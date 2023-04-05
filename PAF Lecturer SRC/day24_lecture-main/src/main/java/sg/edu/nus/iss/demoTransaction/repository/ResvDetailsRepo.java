package sg.edu.nus.iss.demoTransaction.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.demoTransaction.model.ResvDetails;

@Repository
public class ResvDetailsRepo {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String INSERT_SQL = "insert into resvdetails (book_id, resv_id) values (?, ?)";

    public Integer createResvDetails(ResvDetails resv) {
        // Create GeneratedKeyHolder object
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(INSERT_SQL, new String[] {"id"});

                ps.setInt(1, resv.getBookId());
                ps.setInt(2, resv.getResvId());
                return ps;
            }
        };

        jdbcTemplate.update(psc, generatedKeyHolder);

        // Get auto-incremented ID
        Integer returnedId = generatedKeyHolder.getKey().intValue();

        return returnedId;
    }
}
