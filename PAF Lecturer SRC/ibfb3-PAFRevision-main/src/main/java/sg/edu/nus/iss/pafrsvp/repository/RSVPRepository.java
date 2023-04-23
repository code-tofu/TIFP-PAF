package sg.edu.nus.iss.pafrsvp.repository;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.pafrsvp.model.AggregationRSVP;
import sg.edu.nus.iss.pafrsvp.model.RSVP;
import static sg.edu.nus.iss.pafrsvp.repository.Queries.*;

@Repository
public class RSVPRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Autowired
    private MongoTemplate mongoTemplate;

    public RSVP insertRSVP(final RSVP rsvp){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(SQL_INSERT_RSVP, 
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, rsvp.getName());
            ps.setString(2, rsvp.getEmail());
            ps.setString(3, rsvp.getPhone());
            ps.setTimestamp(4, new Timestamp(rsvp.getConfirmationDate().toDateTime().getMillis()));
            ps.setString(5, rsvp.getComments());
            ps.setString(6, rsvp.getFoodType());
            return ps;
        }, keyHolder);

        BigInteger primaryKeyVal = (BigInteger) keyHolder.getKey();
        rsvp.setId(primaryKeyVal.intValue());
        if(rsvp.getId() > 0){
            rsvp.setConfirmationDate(null);
            mongoTemplate.insert(rsvp, "rsvp");
        }

        return rsvp;
    }

    public List<AggregationRSVP> aggregateByFoodType(){

        ProjectionOperation pop = Aggregation.project("name", "foodType");
        GroupOperation gOp = Aggregation.group("foodType")
                .push("name")
                .as("name")
                .count().as("count");
        SortOperation sOp = Aggregation
                .sort(Sort.by(Direction.DESC, "count")); 
        Aggregation pipeline = Aggregation.newAggregation(
                pop, gOp, sOp
        );

        AggregationResults<Document> aa = mongoTemplate.aggregate(pipeline, "rsvp",
                         Document.class);

        List<AggregationRSVP> lstArrgFt = new LinkedList<>();
        Iterator<Document> id = aa.iterator();
        while(id.hasNext()){
            Document d1 = id.next();
            lstArrgFt.add(AggregationRSVP.create(d1));
        }
        return lstArrgFt;
    }

}
