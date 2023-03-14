package com.blaqboxdev.unsplash.Repositories;

import com.blaqboxdev.unsplash.Models.Photo;

import java.util.ArrayList;
import java.util.Arrays;

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

    public List<Photo> findAllSorted() {

        MongoDatabase database = client.getDatabase("unsplash");
        MongoCollection<Document> collection = database.getCollection("photos");

        FindIterable<Document> results = collection.find().sort(Sorts.descending("date_added"));

        List<Photo> docsSorted = new ArrayList<>();

        results.forEach(doc -> docsSorted.add(converter.read(Photo.class,doc)));

        return docsSorted;
    }
}
