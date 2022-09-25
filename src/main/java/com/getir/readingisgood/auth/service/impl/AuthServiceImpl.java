package com.getir.readingisgood.auth.service.impl;

import com.getir.readingisgood.auth.config.JwtUtils;
import com.getir.readingisgood.auth.domain.request.LoginRequest;
import com.getir.readingisgood.auth.domain.request.SignupRequest;
import com.getir.readingisgood.auth.domain.response.MessageResponse;
import com.getir.readingisgood.auth.entity.ERole;
import com.getir.readingisgood.auth.entity.Role;
import com.getir.readingisgood.auth.entity.User;
import com.getir.readingisgood.auth.repository.IRoleRepository;
import com.getir.readingisgood.auth.repository.IUserRepository;
import com.getir.readingisgood.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IRoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Override
    public UserDetailsImpl authenticateUser(LoginRequest loginRequest, Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userDetails;
    }

    @Override
    public User registerUser(SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new RuntimeException("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new RuntimeException("Error: Email is already in use!");
        }
        // Create new user's account
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setName(signUpRequest.getName());
        user.setSurname(signUpRequest.getSurname());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "customer":
                        Role customerRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(customerRole);
                        break;
                    default:
                        throw new RuntimeException("Error: Role not found");
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        return user;
    }

    @Override
    public String generateJwtToken(Authentication authentication) {
        String jwt = jwtUtils.generateJwtToken(authentication);
        return jwt;
    }

    @Override
    public Authentication authenticate(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        return authentication;
    }
}
