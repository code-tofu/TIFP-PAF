package sg.edu.nus.iss.sqlmg.workshop.sqlmg.repository;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoCursor;

import sg.edu.nus.iss.sqlmg.workshop.sqlmg.models.AggrRSVP;
import sg.edu.nus.iss.sqlmg.workshop.sqlmg.models.RSVP;
import sg.edu.nus.iss.sqlmg.workshop.sqlmg.models.RSVPTotalCntMapper;

import static sg.edu.nus.iss.sqlmg.workshop.sqlmg.repository.Queries.*;

@Repository
public class RSVPRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<RSVP> getAllRSVP(String q) {
        // prevent inheritance
        final List<RSVP> rsvps = new LinkedList<>();
        SqlRowSet rs = null;
        // perform the query
        System.out.println("Q>" + q);
        if (q == null) {
            rs = jdbcTemplate.queryForRowSet(SQL_SELECT_ALL_RSVP);
        } else {
            rs = jdbcTemplate.queryForRowSet(SQL_SEARCH_RSVP_BY_NAME, q);
        }

        while (rs.next()) {
            rsvps.add(RSVP.create(rs));
        }
        return rsvps;
    }

    public RSVP searchRSVPByEmail(String email) {
        // prevent inheritance
        final List<RSVP> rsvps = new LinkedList<>();
        // perform the query
        final SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_SEARCH_RSVP_BY_EMAiL, email);

        while (rs.next()) {
            rsvps.add(RSVP.create(rs));
        }
        return rsvps.get(0);
    }

    public RSVP insertRSVP(final RSVP rsvp) {
        KeyHolder keyholder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(conn -> {
                PreparedStatement ps = conn.prepareStatement(SQL_INSERT_RSVP,
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, rsvp.getName());
                ps.setString(2, rsvp.getEmail());
                ps.setString(3, rsvp.getPhone());
                System.out.println("Confirmation date > " + rsvp.getConfirmationDate());
                ps.setTimestamp(4, new Timestamp(rsvp.getConfirmationDate().toDateTime().getMillis()));
                ps.setString(5, rsvp.getComments());
                return ps;
            }, keyholder);
            BigInteger primaryKeyVal = (BigInteger) keyholder.getKey();
            rsvp.setId(primaryKeyVal.intValue());
            if (rsvp.getId() > 0) {
                rsvp.setConfirmationDate(null);
                mongoTemplate.insert(rsvp, "rsvp");
            }
        } catch (DataIntegrityViolationException e) {
            RSVP existingRSVP = searchRSVPByEmail(rsvp.getEmail());
            existingRSVP.setComments(rsvp.getComments());
            existingRSVP.setName(rsvp.getName());
            existingRSVP.setPhone(rsvp.getPhone());
            existingRSVP.setConfirmationDate(rsvp.getConfirmationDate());
            if (updateRSVP(existingRSVP))
                rsvp.setId(existingRSVP.getId());
        }

        return rsvp;
    }

    public boolean updateRSVP(final RSVP rsvp) {
        return jdbcTemplate.update(SQL_UPDATE_RSVP_BY_EMAIL,
                rsvp.getName(),
                rsvp.getPhone(),
                new Timestamp(rsvp.getConfirmationDate().toDateTime().getMillis()),
                rsvp.getComments(),
                rsvp.getEmail()) > 0;
    }

    public Integer getTotalRSVP() {
        // perform the query
        List<RSVP> rsvps = jdbcTemplate.query(SQL_TOTAL_CNT_RSVP, new RSVPTotalCntMapper(),
                new Object[] {});

        return rsvps.get(0).getTotalCnt();
    }

    public List<AggrRSVP> aggregateRSVPByFoodType() {
        System.out.println("aggregateRSVPByFoodType");
        GroupOperation grpByFoodType = Aggregation
                .group("foodType")
                .push("foodType")
                .as("foodType")
                .count().as("count");
        SortOperation sortByCount = Aggregation
                .sort(Sort.by(Direction.DESC,
                        "count"));

        Aggregation pipeline = Aggregation
                .newAggregation(grpByFoodType, sortByCount);
        AggregationResults<Document> results = mongoTemplate
                .aggregate(pipeline, "rsvp",
                        Document.class);

        List<AggrRSVP> arrgArr = new LinkedList<AggrRSVP>();
        Iterator<Document> cursor = results.iterator();
        while (cursor.hasNext()) {
            Document doc = cursor.next();
            arrgArr.add(AggrRSVP.create(doc));
        }
        return arrgArr;
    }
}
