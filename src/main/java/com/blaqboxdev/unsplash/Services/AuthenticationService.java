package com.blaqboxdev.unsplash.Services;


import com.blaqboxdev.unsplash.Models.DTO.AuthenticationResponse;

import com.blaqboxdev.unsplash.Models.DTO.ProfileDTO;
import com.blaqboxdev.unsplash.Models.DTO.RegistrationResponse;
import com.blaqboxdev.unsplash.Models.Requests.AuthenticationRequest;
import com.blaqboxdev.unsplash.Models.Requests.RegisterRequest;



public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);

    RegistrationResponse register(RegisterRequest request);

    ProfileDTO getActiveSessionUser();
}
