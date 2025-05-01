package com.blaqboxdev.unsplash.Services;

import com.blaqboxdev.unsplash.Models.FollowRequest;
import com.blaqboxdev.unsplash.Models.Profile;
import com.blaqboxdev.unsplash.Models.ProfileRequest;
import com.blaqboxdev.unsplash.Models.User;
import com.blaqboxdev.unsplash.Repositories.ProfileRepository;
import com.blaqboxdev.unsplash.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProfileService {

    @Autowired
    private final ProfileRepository profileRepository;

    @Autowired
    private final UserRepository userRepository;

    public Profile createProfile(ProfileRequest details){

        User user = userRepository.findById(details.getUserId()).orElseThrow();

        Profile profile = Profile.builder()
                .user(user)
                .username(details.getUsername())
                .avatar(details.getAvatar())
                .fullName(details.getFullName())
                .followers(new ArrayList<>())
                .build();

        return profileRepository.save(profile);
    }

    public Profile updateProfile(Profile updated){
        return profileRepository.save(updated);
    }

    public Optional<Profile> fetchProfile(String username){
        return profileRepository.findByUsername(username);
    }

    public boolean followProfile(FollowRequest details){
        Profile toFollow = profileRepository.findByUsername(details.getUsernameToFollow()).orElse(null);
        Profile current = profileRepository.findByUsername(details.getCurrentUsername()).orElse(null);

        if(toFollow != null && current != null){
            toFollow.addFollower(current);
            return true;
        }
        return false;
    }
}
