package com.getir.readingisgood.util;

import com.getir.readingisgood.auth.domain.request.SignupRequest;
import com.getir.readingisgood.data.dto.CustomerDTO;

import java.util.HashSet;
import java.util.Set;

public class CreateCustomerHelper {
    enum Role{
        customer;
    }

    public static SignupRequest mapToSignUpRequest(CustomerDTO customerDTO) {
        SignupRequest signupRequest = new SignupRequest();
        Set<String> roles = new HashSet<>();
        roles.add(Role.customer.toString());
        signupRequest.setName(customerDTO.getName());
        signupRequest.setSurname(customerDTO.getSurname());
        signupRequest.setPassword(customerDTO.getEmail());
        signupRequest.setRoles(roles);
        signupRequest.setUsername(customerDTO.getEmail());
        signupRequest.setEmail(customerDTO.getEmail());
        return signupRequest;
    }

}
