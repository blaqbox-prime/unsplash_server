package com.blaqboxdev.unsplash.Models.Entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "collections")
public class Collection {

    @MongoId
    private String _id;
    private String author;
    private String title;

    @Builder.Default
    private Date dateCreated = new Date();

    @Builder.Default
    @DocumentReference(collection = "photos")
    private List<Image> images = new ArrayList<>();

    public void addImage(Image image){
        if(images.contains(image)) return;
        images.add(image);
    }

    public void removeImage(Image image){
        images.remove(image);
    }
}
