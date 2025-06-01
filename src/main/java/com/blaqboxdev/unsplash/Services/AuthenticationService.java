package com.blaqboxdev.unsplash.Services;

import com.blaqboxdev.unsplash.Config.JwtService;
import com.blaqboxdev.unsplash.Exceptions.UserNotFoundException;
import com.blaqboxdev.unsplash.Models.*;
import com.blaqboxdev.unsplash.Models.DTO.AuthenticationResponse;
import com.blaqboxdev.unsplash.Models.Entities.User;
import com.blaqboxdev.unsplash.Models.Requests.AuthenticationRequest;
import com.blaqboxdev.unsplash.Models.Requests.RegisterRequest;
import com.blaqboxdev.unsplash.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


public interface AuthenticationService {
    public AuthenticationResponse authenticate(AuthenticationRequest request);

    public AuthenticationResponse register(RegisterRequest request);
}
