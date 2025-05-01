package com.blaqboxdev.unsplash.Repositories;

import com.blaqboxdev.unsplash.Models.Image;

import java.util.ArrayList;

import com.mongodb.client.*;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.stereotype.Component;

@Component
public class PhotoImp implements PhotoRepositoryCustom{
    @Autowired
    MongoConverter converter;

    @Autowired
    MongoClient client;

    public List<Image> findAllSorted() {

        MongoDatabase database = client.getDatabase("unsplash");
        MongoCollection<Document> collection = database.getCollection("photos");

        FindIterable<Document> results = collection.find().sort(Sorts.descending("date_added"));

        List<Image> docsSorted = new ArrayList<>();

        results.forEach(doc -> docsSorted.add(converter.read(Image.class,doc)));

        return docsSorted;
    }
}
