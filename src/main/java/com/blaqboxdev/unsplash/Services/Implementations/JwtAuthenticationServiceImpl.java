package com.blaqboxdev.unsplash.Services.Implementations;

import com.blaqboxdev.unsplash.Config.JwtService;
import com.blaqboxdev.unsplash.Exceptions.DuplicateUserEmailAlreadyExists;
import com.blaqboxdev.unsplash.Exceptions.UserNotFoundException;
import com.blaqboxdev.unsplash.Models.DTO.AuthenticationResponse;
import com.blaqboxdev.unsplash.Models.DTO.ProfileDTO;
import com.blaqboxdev.unsplash.Models.DTO.RegistrationResponse;
import com.blaqboxdev.unsplash.Models.DTO.UserDTO;
import com.blaqboxdev.unsplash.Models.Entities.User;
import com.blaqboxdev.unsplash.Models.Requests.AuthenticationRequest;
import com.blaqboxdev.unsplash.Models.Requests.RegisterRequest;
import com.blaqboxdev.unsplash.Models.Role;
import com.blaqboxdev.unsplash.Repositories.UserRepository;
import com.blaqboxdev.unsplash.Services.AuthenticationService;
import com.blaqboxdev.unsplash.Services.ProfileService;
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
    private final ProfileService profileService;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("user does not exist"));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .profile(new ProfileDTO(profileService.getProfileByUser(user)))
                .build();
    }

    public RegistrationResponse register(RegisterRequest request) {
        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        User savedUser = null;
        UserDTO userDTO = null;
        try {
            savedUser = repository.save(user);
            userDTO = new UserDTO(savedUser);
        } catch (RuntimeException e) {
            throw new DuplicateUserEmailAlreadyExists("User with email " + request.getEmail() + " already exists");
        }
        var jwtToken = jwtService.generateToken(user);
        return RegistrationResponse.builder()
                .token(jwtToken)
                .user(userDTO)
                .build();
    }
}
