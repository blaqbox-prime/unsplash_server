package com.blaqboxdev.unsplash.Models.Entities;

import com.blaqboxdev.unsplash.Models.DTO.ProfileDTO;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(collection = "profiles")
public class Profile {

    @Id
    private String _id;

    @Indexed(unique = true)
    private String username;

    private String fullName;

//    @DocumentReference(lazy = true)
    private ArrayList<Profile> followers;

    private String avatar;

    @DocumentReference
    private User user;


    public void addFollower(Profile followee){
        this.followers.add(followee);
    }

//    public void removeFollower(Profile followee){
//        this.followers.remove(followee);
//    }

    public static ProfileDTO toDTO(Profile profile) {
        return ProfileDTO.builder()
                .username(profile.getUsername())
                .fullName(profile.getFullName())
                .avatar(profile.getAvatar())
                .followers(profile.getFollowers())
                ._id(profile.get_id())
                .build();
    }
}
