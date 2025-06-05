package com.blaqboxdev.unsplash.Services.Implementations;

import com.blaqboxdev.unsplash.Config.JwtService;
import com.blaqboxdev.unsplash.Exceptions.UserNotFoundException;
import com.blaqboxdev.unsplash.Models.DTO.AuthenticationResponse;
import com.blaqboxdev.unsplash.Models.Entities.User;
import com.blaqboxdev.unsplash.Models.Requests.AuthenticationRequest;
import com.blaqboxdev.unsplash.Models.Requests.RegisterRequest;
import com.blaqboxdev.unsplash.Models.Role;
import com.blaqboxdev.unsplash.Repositories.UserRepository;
import com.blaqboxdev.unsplash.Services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("user does not exist"));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .email(request.getEmail())
                .build();
    }

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .email(user.getEmail())
                .build();
    }
}
