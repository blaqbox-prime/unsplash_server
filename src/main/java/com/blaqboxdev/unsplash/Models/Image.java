package com.blaqboxdev.unsplash.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "photos")
public class Image {
    private String label;

    private List<String> tags;

    @MongoId
    private String _id;

    private String url;

    private LocalDateTime date_added;

    private int likes = 0;

    @DocumentReference(collection = "profiles")
    private Profile user;

    public void increment_likes(){
        this.likes = this.likes + 1;
    }

    public void decrement_likes(){
        if(this.likes != 0){
            this.likes = this.likes - 1;
        }
    }

}
