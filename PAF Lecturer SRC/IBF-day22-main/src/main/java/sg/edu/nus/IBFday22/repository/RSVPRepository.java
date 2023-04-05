package sg.edu.nus.IBFday22.repository;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import sg.edu.nus.IBFday22.model.RSVP;
import sg.edu.nus.IBFday22.model.RSVPTotalCountMapper;

import static sg.edu.nus.IBFday22.repository.DBQueries.*;

@Repository
public class RSVPRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    /*
     * 
     * fetch all rsvp
     */

    public List<RSVP> getAllRSVP() {
        List<RSVP> rsvps = new ArrayList<RSVP>();
        SqlRowSet rs = null;
        rs = jdbcTemplate.queryForRowSet(SELECT_ALL_RSVP);
        while (rs.next())
            rsvps.add(RSVP.create(rs));
        return rsvps;
    }

    public List<RSVP> getRSVPByName(String name) {
        List<RSVP> rsvps = new ArrayList<RSVP>();
        SqlRowSet rs = null;

        rs = jdbcTemplate.queryForRowSet(SELECT_RSVP_BY_NAME, new Object[] { "%" + name + "%" });

        while (rs.next())
            rsvps.add(RSVP.create(rs));
        return rsvps;
    }

    private RSVP getRSVPByEmail(String email) {
        List<RSVP> rsvpList = new ArrayList<>();
        SqlRowSet rs = jdbcTemplate.queryForRowSet(SELECT_RSVP_BY_EMAIL, email);

        System.out.println("checking resultset ----> " + Objects.isNull(rs));

        while (rs.next()) {
            System.out.println("inside while loop");
            rsvpList.add(RSVP.create(rs));
        }

        System.out.println("size of rsvp list ---- > " + rsvpList.size());
        if (rsvpList.size() == 0)
            return null;
        return rsvpList.get(0);
    }

    public RSVP createRsvp(RSVP rsvp) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        RSVP existingRSVP = getRSVPByEmail(rsvp.getEmail());

        if (Objects.isNull(existingRSVP)) {

            System.out.println("inside If loop--->");
            // insert record
            jdbcTemplate.update(conn -> {
                PreparedStatement statement = conn.prepareStatement(INSERT_NEW_RSVP, Statement.RETURN_GENERATED_KEYS);

                statement.setString(1, rsvp.getName());
                statement.setString(2, rsvp.getEmail());
                statement.setString(3, rsvp.getPhone());
                statement.setTimestamp(4, new Timestamp(rsvp.getConfirmationDate().toDateTime().getMillis()));
                statement.setString(5, rsvp.getComments());
                return statement;
            }, keyHolder);

            BigInteger primaryKey = (BigInteger) keyHolder.getKey();

            rsvp.setId(primaryKey.intValue());

        } else {
            System.out.println("inside else loop--->");
            // update existing record
            existingRSVP.setName(rsvp.getName());
            existingRSVP.setPhone(rsvp.getPhone());
            existingRSVP.setConfirmationDate(rsvp.getConfirmationDate());
            existingRSVP.setComments(rsvp.getComments());

            boolean isUpdated = updateRSVP(existingRSVP);

            if (isUpdated) {
                rsvp.setId(existingRSVP.getId());
            }

        }

        return rsvp;
    }

    public boolean updateRSVP(RSVP existingRSVP) {
        return jdbcTemplate.update(UPDATE_RSVP_BY_EMAIL,
                existingRSVP.getName(),
                existingRSVP.getPhone(),
                new Timestamp(existingRSVP.getConfirmationDate().toDateTime().getMillis()),
                existingRSVP.getComments(),
                existingRSVP.getEmail()) > 0;
    }

    public Long getTotalRSVPCount() {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(TOTAL_RSVP_COUNT);
        return (Long) rows.get(0).get("total_count");
    }

}
