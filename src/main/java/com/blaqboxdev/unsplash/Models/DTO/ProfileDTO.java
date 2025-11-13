package com.blaqboxdev.unsplash.Models.DTO;


import com.blaqboxdev.unsplash.Models.Entities.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProfileDTO implements Serializable {
    private String _id;

    private String username;

    private String fullName;

    private ArrayList<Profile> followers;

    private String avatar;

    private UserDTO user;

    public ProfileDTO(Profile profile) {
        this._id = profile.get_id();
        this.username = profile.getUsername();
        this.fullName = profile.getFullName();
        this.followers = profile.getFollowers();
        this.avatar = profile.getAvatar();
        this.user = new UserDTO(profile.getUser());
    }
}
