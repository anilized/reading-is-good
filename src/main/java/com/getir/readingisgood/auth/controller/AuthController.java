package com.getir.readingisgood.auth.controller;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import com.getir.readingisgood.auth.domain.request.LoginRequest;
import com.getir.readingisgood.auth.domain.request.SignupRequest;
import com.getir.readingisgood.auth.domain.response.JwtResponse;
import com.getir.readingisgood.auth.domain.response.MessageResponse;
import com.getir.readingisgood.auth.service.AuthService;
import com.getir.readingisgood.auth.service.impl.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authService.authenticate(loginRequest);
        String jwt = authService.generateJwtToken(authentication);
        UserDetailsImpl userDetails = authService.authenticateUser(loginRequest, authentication);
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .toList();
        return ResponseEntity.ok(new JwtResponse(jwt,"Bearer",
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        authService.registerUser(signUpRequest);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
