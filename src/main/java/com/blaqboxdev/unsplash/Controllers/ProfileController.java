package com.blaqboxdev.unsplash.Controllers;

import com.blaqboxdev.unsplash.Models.FollowRequest;
import com.blaqboxdev.unsplash.Models.Profile;
import com.blaqboxdev.unsplash.Models.ProfileRequest;
import com.blaqboxdev.unsplash.Services.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RequiredArgsConstructor
@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private final ProfileService profileService;

    @PostMapping(path = "/create")
    private ResponseEntity<Profile> createProfile(@RequestBody ProfileRequest request){
//        Bad Request
        if(request.getUsername().isEmpty() || request.getUserId().isEmpty()){
            return ResponseEntity.badRequest().body(null);
        }
//        Server Error

        Profile profile = profileService.createProfile(request);
        if (profile == null) {
            return ResponseEntity.internalServerError().build();
        }

//        Ok
        return ResponseEntity.ok(profileService.createProfile(request));
    }

    @GetMapping(path = "/{username}")
    private ResponseEntity<Profile> fetchProfileByUsername(@PathVariable String username){
//        Not Found
        Profile profile = profileService.fetchProfile(username).orElse(null);
        if (profile == null) {
            return ResponseEntity.notFound().build();
        }
//        Ok
        return ResponseEntity.ok(profile);
    }

    @PutMapping(path = "/{username}/update")
    private ResponseEntity<Profile> updateProfile(@PathVariable String username, @RequestBody Profile updated){
//        NotFound
        Profile profile = profileService.fetchProfile(username).orElse(null);
        if (profile == null) {
            return ResponseEntity.notFound().build();
        }
//        ok
        profile = profileService.updateProfile(updated);
        System.out.println("==============================");
        System.out.println(profile.toString());
        System.out.println("==============================");
        return ResponseEntity.ok(profile);
    }

    @PostMapping(path = "/follow")
    private ResponseEntity<HashMap<String, String>> followUser(@RequestBody FollowRequest request){

    }

}
