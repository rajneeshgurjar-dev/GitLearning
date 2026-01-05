package com.example.zoom.repository;

import com.example.zoom.dto.UserWithOrdersDto;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserAggregationRepositoryImpl implements UserAggregationRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public UserWithOrdersDto getUserWithOrders(String userId) {

        ObjectId objectId;
        try {
            objectId = new ObjectId(userId);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid userId format: " + userId);
        }

        MatchOperation matchUser =
                Aggregation.match(Criteria.where("_id").is(objectId));

        LookupOperation lookupOrders =
                Aggregation.lookup(
                        "orders",   // from collection
                        "_id",      // users._id (ObjectId)
                        "userId",   // orders.userId (ObjectId)
                        "orders"
                );

        Aggregation aggregation =
                Aggregation.newAggregation(matchUser, lookupOrders);

        return mongoTemplate
                .aggregate(aggregation, "users", UserWithOrdersDto.class)
                .getUniqueMappedResult();
    }
}

