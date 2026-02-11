package com.blaqboxdev.unsplash.Models.Entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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

    @Id
    private String _id;

    private String url;
    private String thumbnail;

    private LocalDateTime date_added;

    @Builder.Default
    private int likes = 0;

    @Field("profile")
    private String profile;

//    public void increment_likes(){
//        this.likes = this.likes + 1;
//    }
//
//    public void decrement_likes(){
//        if(this.likes != 0){
//            this.likes = this.likes - 1;
//        }
//    }

}
