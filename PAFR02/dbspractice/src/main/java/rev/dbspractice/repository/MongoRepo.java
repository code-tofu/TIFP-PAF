package rev.dbspractice.repository;

import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ObjectOperators;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

@Repository
public class MongoRepo {

    @Autowired
    MongoTemplate mongoTemplate;

    public List<Document> getAllAccounts(int limit, int offset, int sort) {
        Query query = new Query();

        // note that if the result is to be sorted, then aggregation should be used
        if (sort == 1)
            query.limit(limit).skip(offset).with(Sort.by(Sort.Direction.ASC, "account_id"));
        if (sort == -1)
            query.limit(limit).skip(offset).with(Sort.by(Sort.Direction.DESC, "account_id"));

        return mongoTemplate.find(query, Document.class, "accounts");
    }

    public Document getAccountbyID(int id) {
        Query query = new Query(Criteria.where("account_id").is(id));
        return mongoTemplate.findOne(query, Document.class, "accounts");
    }

    public Document getTierDetails(ObjectId id) {
        MatchOperation matchObjectID = Aggregation.match(Criteria.where("_id").is(id));
        ProjectionOperation projectTierDetails = Aggregation.project().and(ObjectOperators.valueOf("tier_and_details")
                .toArray())
                .as("tierDetails");
        Aggregation pipeline = Aggregation.newAggregation(matchObjectID, projectTierDetails);

        AggregationResults<Document> result = mongoTemplate.aggregate(pipeline, "customers", Document.class);
        return result.iterator().next();
        // getMappedResults() returns List<Document> based on
        // AggregationResults<Document>
        // public class AggregationResults<T> implements Iterable<T>
    }

    public List<Document> getCustomerbyName(String username) {
        Query query = new Query(Criteria.where("username").regex(username, "i"));
        return mongoTemplate.find(query, Document.class, "customers");
    }
}
