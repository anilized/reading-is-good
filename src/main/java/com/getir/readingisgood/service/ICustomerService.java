package com.getir.readingisgood.service;

import com.getir.readingisgood.data.dto.CustomerDTO;

import java.util.Optional;

public interface ICustomerService {
    CustomerDTO createCustomer(CustomerDTO customerDTO);
    CustomerDTO findById(Long id);
    Optional<CustomerDTO> getByEmail(String email);
}
