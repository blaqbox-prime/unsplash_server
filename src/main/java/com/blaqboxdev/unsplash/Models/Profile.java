package com.blaqboxdev.unsplash.Models;

import jakarta.persistence.UniqueConstraint;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(collection = "profiles")
public class Profile {

    @MongoId
    private String _id;

    private String userId;

    @Indexed(unique = true)
    private String username;
    @Setter
    @Getter
    private ArrayList<Profile> followers;
    private String profilePicture;
    @Setter
    @Getter
    private ArrayList<Photo> photos;

    public void addFollower(Profile followee){
        this.followers.add(followee);
    }

    public void removeFollower(Profile followee){
        this.followers.remove(followee);
    }

    public void addPhoto(Photo photo){
        this.photos.add(photo);
    }

    public void removePhoto(Photo photo){
        this.photos.remove(photo);
    }
}
