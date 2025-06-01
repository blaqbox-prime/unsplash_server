package com.blaqboxdev.unsplash.Repositories;

import com.blaqboxdev.unsplash.Models.Entities.Image;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class SearchImpl implements SearchRepo{

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Image> findByText(List<String> keywords) {
        List<Criteria> criteriaList = new ArrayList<>();

        for (String keyword : keywords) {
            Criteria labelCriteria = Criteria.where("label").regex(".*" + keyword + ".*", "i");
            Criteria tagsCriteria = Criteria.where("tags").elemMatch(Criteria.where("$regex").is("^" + keyword +"$").and("$options").is("i"));
            criteriaList.add(new Criteria().orOperator(labelCriteria, tagsCriteria));
        }

        Query query = new Query(new Criteria().orOperator(criteriaList.toArray(new Criteria[0])));
        return mongoTemplate.find(query, Image.class);
    }
}
