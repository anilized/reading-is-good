package com.getir.readingisgood.auth.service.impl;

import com.getir.readingisgood.auth.config.JwtUtils;
import com.getir.readingisgood.auth.domain.request.LoginRequest;
import com.getir.readingisgood.auth.domain.request.SignupRequest;
import com.getir.readingisgood.auth.entity.ERole;
import com.getir.readingisgood.auth.entity.Role;
import com.getir.readingisgood.auth.entity.User;
import com.getir.readingisgood.auth.repository.IRoleRepository;
import com.getir.readingisgood.auth.repository.IUserRepository;
import com.getir.readingisgood.auth.service.AuthService;
import com.getir.readingisgood.data.entity.Customer;
import com.getir.readingisgood.data.repository.CustomerRepository;
import com.getir.readingisgood.exception.AuthNotSupported;
import com.getir.readingisgood.exception.CustomerAlreadyExistsException;
import com.getir.readingisgood.exception.UsernameAlreadyInUseException;
import com.getir.readingisgood.util.PasswordEncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
    CustomerRepository customerRepository;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IRoleRepository roleRepository;
/*
    @Autowired
    PasswordEncoder encoder;*/

    @Autowired
    PasswordEncoderUtil passwordEncoderUtil;

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
            throw new UsernameAlreadyInUseException();
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new CustomerAlreadyExistsException();
        }
        // Create new user's account
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setName(signUpRequest.getName());
        user.setSurname(signUpRequest.getSurname());
        user.setPassword(passwordEncoderUtil.encodePassword(signUpRequest.getPassword()));
        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();
        strRoles.forEach(role -> {
            switch (role) {
                case "admin":
                    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new AuthNotSupported());
                    roles.add(adminRole);
                    break;
                case "customer":
                    Role customerRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
                            .orElseThrow(() -> new AuthNotSupported());
                    roles.add(customerRole);
                    break;
                default:
                    throw new AuthNotSupported();
            }
        });
        user.setRoles(roles);
        userRepository.save(user);
        for(Role role : user.getRoles()) {
            if(role.getName().equals(ERole.ROLE_CUSTOMER)) {
                Customer customer = new Customer();
                customer.setCustomerId(user.getId());
                customer.setName(user.getName());
                customer.setSurname(user.getSurname());
                customer.setEmail(user.getEmail());
                customer.setPassword(user.getPassword());
                customerRepository.save(customer);
            }
        }

        return user;
    }
    @Override
    public User registerUserAsAdmin(SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new UsernameAlreadyInUseException();
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new CustomerAlreadyExistsException();
        }
        // Create new user's account
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setName(signUpRequest.getName());
        user.setSurname(signUpRequest.getSurname());
        user.setPassword(signUpRequest.getPassword());
        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();
        strRoles.forEach(role -> {
            switch (role) {
                case "admin":
                    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new AuthNotSupported());
                    roles.add(adminRole);
                    break;
                case "customer":
                    Role customerRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
                            .orElseThrow(() -> new AuthNotSupported());
                    roles.add(customerRole);
                    break;
                default:
                    throw new AuthNotSupported();
            }
        });
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
