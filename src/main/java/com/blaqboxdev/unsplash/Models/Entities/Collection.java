package com.blaqboxdev.unsplash.Models.Entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "collections")
public class Collection {

    @Id
    private String _id;

    private String author;
    private String title;

    @Builder.Default
    private Date dateCreated = new Date();

    @DocumentReference(lazy = true)
    private List<Image> images;

    public void addImage(Image image){
        if(images.contains(image)) return;
        images.add(image);
    }

    public boolean removeImage(Image image){
        return images.remove(image);
    }
}
