package com.blaqboxdev.unsplash.Services;


import com.blaqboxdev.unsplash.Models.DTO.AuthenticationResponse;

import com.blaqboxdev.unsplash.Models.Requests.AuthenticationRequest;
import com.blaqboxdev.unsplash.Models.Requests.RegisterRequest;



public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);

    AuthenticationResponse register(RegisterRequest request);
}
