package com.blaqboxdev.unsplash.Repositories;

import com.blaqboxdev.unsplash.Models.Image;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class SearchImpl implements SearchRepo{

    @Autowired
    MongoConverter converter;

    @Autowired
    MongoClient client;


    @Override
    public List<Image> findByText(String text) {

        final List<Image> images = new ArrayList<>();

        MongoDatabase database = client.getDatabase("unsplash");
        MongoCollection<Document> collection = database.getCollection("photos");

        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$search",
                        new Document("index", "default")
                                .append("text",
                                        new Document("query", text)
                                                .append("path", "label"))),
                new Document("$sort",
                        new Document("date_added", -1L))));

        result.forEach(doc -> images.add(converter.read(Image.class,doc)));

        return images;
    }
}
