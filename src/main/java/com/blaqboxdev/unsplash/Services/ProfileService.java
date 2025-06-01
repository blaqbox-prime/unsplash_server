package com.blaqboxdev.unsplash.Services;

import com.blaqboxdev.unsplash.Exceptions.ProfileNotFoundException;
import com.blaqboxdev.unsplash.Models.Requests.FollowRequest;
import com.blaqboxdev.unsplash.Models.Entities.Profile;
import com.blaqboxdev.unsplash.Models.Requests.ProfileRequest;
import com.blaqboxdev.unsplash.Models.Entities.User;
import com.blaqboxdev.unsplash.Repositories.ProfileRepository;
import com.blaqboxdev.unsplash.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;


public interface ProfileService {

    public Profile getProfileById(String id);
    public Profile createProfile(ProfileRequest profileRequest);
    public Profile updateProfile(Profile profile) throws ProfileNotFoundException;
    public Profile getProfileByUsername(String username) throws ProfileNotFoundException;
    public boolean followProfile(FollowRequest request);
}
