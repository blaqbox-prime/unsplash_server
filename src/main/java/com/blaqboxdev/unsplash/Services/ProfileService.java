package com.blaqboxdev.unsplash.Services;

import com.blaqboxdev.unsplash.Models.FollowRequest;
import com.blaqboxdev.unsplash.Models.Profile;
import com.blaqboxdev.unsplash.Models.ProfileRequest;
import com.blaqboxdev.unsplash.Repositories.ProfileRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public Profile createProfile(ProfileRequest details){
        Profile profile = Profile.builder()
                .userId(details.getUserId())
                .username(details.getUsername())
                .profilePicture("")
                .followers(new ArrayList<>())
                .photos(new ArrayList<>())
                .build();

        return profileRepository.save(profile);
    }

    public Profile updateProfile(Profile updated){
        return profileRepository.save(updated);
    }

    public Optional<Profile> fetchProfile(String username){
        return profileRepository.findByUsername(username);
    }

    public void followProfile(FollowRequest details){
        Profile toFollow = profileRepository.findByUsername(details.getUsernameToFollow()).orElse(null);
        Profile current = profileRepository.findByUsername(details.getCurrentUsername()).orElse(null);

        if(toFollow != null && current != null){
            toFollow.addFollower(current);
        }

    }
}
