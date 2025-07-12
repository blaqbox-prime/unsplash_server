package com.blaqboxdev.unsplash.Controllers;


import com.blaqboxdev.unsplash.Models.DTO.ProfileDTO;
import com.blaqboxdev.unsplash.Models.DTO.RegistrationResponse;
import com.blaqboxdev.unsplash.Models.Requests.AuthenticationRequest;
import com.blaqboxdev.unsplash.Models.DTO.AuthenticationResponse;
import com.blaqboxdev.unsplash.Models.Requests.RegisterRequest;
import com.blaqboxdev.unsplash.Services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    @Autowired
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        AuthenticationResponse response = service.authenticate(request);
        System.out.println(response.toString());
        if (!response.toString().isBlank()) return ResponseEntity.ok(response);
        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping("/me")
    public ResponseEntity<?> checkIfSessionTokenValid(){
        try {
            ProfileDTO response = service.getActiveSessionUser();
            System.out.println(response.toString());
            return  ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not authenticated");
        }
    }

}
