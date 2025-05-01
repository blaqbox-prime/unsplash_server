package com.blaqboxdev.unsplash.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class ProfileRequest {

    private final String fullName;
    private final String userId;
    private final String username;
    private final String avatar;
}
