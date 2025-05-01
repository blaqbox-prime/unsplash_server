package com.blaqboxdev.unsplash.Models;

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

    @Indexed(unique = true)
    private String username;

    private String fullName;

    private ArrayList<Profile> followers;

    private String avatar;

    @DocumentReference(collection = "users")
    private User user;


    public void addFollower(Profile followee){
        this.followers.add(followee);
    }

    public void removeFollower(Profile followee){
        this.followers.remove(followee);
    }

}
