package com.getir.readingisgood.auth.service;

import com.getir.readingisgood.auth.domain.request.LoginRequest;
import com.getir.readingisgood.auth.domain.request.SignupRequest;
import com.getir.readingisgood.auth.entity.User;
import com.getir.readingisgood.auth.service.impl.UserDetailsImpl;
import com.getir.readingisgood.exception.AuthNotSupported;
import org.springframework.security.core.Authentication;

public interface AuthService {
    UserDetailsImpl authenticateUser(LoginRequest loginRequest, Authentication authentication);
    User registerUser(SignupRequest signupRequest) throws AuthNotSupported;

    String generateJwtToken(Authentication authentication);

    Authentication authenticate(LoginRequest loginRequest);

}
