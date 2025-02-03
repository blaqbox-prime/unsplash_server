package com.blaqboxdev.unsplash.Models;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class FollowRequest {
    private String usernameToFollow;
    private String currentUsername;
}
