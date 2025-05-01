package com.blaqboxdev.unsplash.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.MongoId;

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

    private String title;
    private Date dateCreated = new Date();
    @DocumentReference(collection = "photos")
    private List<Image> images;

    public void addImage(Image image){
        images.add(image);
    }

    public void removeImage(Image image){
        images.remove(image);
    }
}
