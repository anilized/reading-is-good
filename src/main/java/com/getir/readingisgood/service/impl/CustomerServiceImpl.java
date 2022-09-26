package com.getir.readingisgood.service.impl;

import com.getir.readingisgood.auth.domain.request.SignupRequest;
import com.getir.readingisgood.auth.repository.IUserRepository;
import com.getir.readingisgood.auth.service.AuthService;
import com.getir.readingisgood.data.dto.CustomerDTO;
import com.getir.readingisgood.data.entity.Customer;
import com.getir.readingisgood.data.mapper.CustomerMapper;
import com.getir.readingisgood.data.repository.CustomerRepository;
import com.getir.readingisgood.service.ICustomerService;
import com.getir.readingisgood.util.CreateCustomerHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class CustomerServiceImpl implements ICustomerService {

    final CustomerMapper customerMapper;

    final IUserRepository userRepository;

    final AuthService authService;

    final CustomerRepository customerRepository;

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        SignupRequest signupRequest = CreateCustomerHelper.mapToSignUpRequest(customerDTO);
        try {
            // REGISTER USER WITH AUTH SERVICE
            authService.registerUser(signupRequest);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        Customer customer = customerMapper.toEntity(customerDTO);
        customer.setPassword(customerDTO.getEmail());

        // ADD REGISTERED USER TO TABLE IF DOES NOT REGISTERED BEFORE
        getByEmail(customer.getEmail()).ifPresentOrElse(customerDTO1 -> {
            throw new RuntimeException("Customer allready exists");
        }, () -> customerRepository.saveAndFlush(customer));
        return customerDTO;
    }

    @Override
    public CustomerDTO findById(Long id) {
        return customerRepository.findById(id).map(customerMapper::toDTO).orElseThrow(RuntimeException::new);
    }

    @Override
    public Optional<CustomerDTO> getByEmail(String email) {
        return customerRepository.findByEmail(email).map(customerMapper::toDTO);
    }
}
