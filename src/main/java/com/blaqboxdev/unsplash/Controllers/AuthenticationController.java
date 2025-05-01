package com.blaqboxdev.unsplash.Controllers;


import com.blaqboxdev.unsplash.Models.AuthenticationRequest;
import com.blaqboxdev.unsplash.Models.AuthenticationResponse;
import com.blaqboxdev.unsplash.Models.RegisterRequest;
import com.blaqboxdev.unsplash.Services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private final AuthenticationService service;

    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        AuthenticationResponse response = service.authenticate(request);
        System.out.println(response.toString());
        if (!response.toString().isBlank()) return ResponseEntity.ok(response);
        return ResponseEntity.badRequest().body(null);
    }

}
