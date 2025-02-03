package com.blaqboxdev.unsplash.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProfileRequest {

    private String userId;
    private String username;
}
