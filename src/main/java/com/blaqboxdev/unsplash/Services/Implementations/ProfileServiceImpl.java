package com.blaqboxdev.unsplash.Services.Implementations;

import com.blaqboxdev.unsplash.Exceptions.ProfileNotFoundException;
import com.blaqboxdev.unsplash.Exceptions.UserNotFoundException;
import com.blaqboxdev.unsplash.Models.Entities.Profile;
import com.blaqboxdev.unsplash.Models.Entities.User;
import com.blaqboxdev.unsplash.Models.Requests.FollowRequest;
import com.blaqboxdev.unsplash.Models.Requests.ProfileRequest;
import com.blaqboxdev.unsplash.Repositories.ProfileRepository;
import com.blaqboxdev.unsplash.Repositories.UserRepository;
import com.blaqboxdev.unsplash.Services.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private final ProfileRepository profileRepository;

    @Autowired
    private final UserRepository userRepository;

    @Override
    public Profile getProfileById(String id) {
        return null;
    }

    public Profile createProfile(ProfileRequest details){

        User user = userRepository.findById(details.getUserId()).orElseThrow(
                () -> new UserNotFoundException("user with id: " + details.getUserId() + " not found")
        );

        Profile profile = Profile.builder()
                .user(user)
                .username(details.getUsername())
                .avatar(details.getAvatar())
                .fullName(details.getFullName())
                .followers(new ArrayList<>())
                .build();

        return profileRepository.save(profile);
    }

    public Profile updateProfile(Profile updated) throws ProfileNotFoundException {

        boolean found = profileRepository.existsById(updated.get_id());
       if(!found) throw new ProfileNotFoundException("Profile with id " + updated.get_id() + " does not exist");

       return profileRepository.save(updated);
    }

    @Override
    public Profile getProfileByUsername(String username) throws ProfileNotFoundException {
        return profileRepository.findByUsername(username).orElseThrow(() -> new ProfileNotFoundException("profile with username " + username + " not found"));
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

    @Override
    public Profile getProfileByUserId(String userId) {
        return profileRepository.findByUserId(userId).orElseThrow(() -> new ProfileNotFoundException("This user does not have a profile"));
    }

    @Override
    public Profile getProfileByUser(User user) {
        return profileRepository.findByUser(user).orElseThrow(() -> new ProfileNotFoundException("This user does not have a profile"));
    }

    @Override
    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

}
