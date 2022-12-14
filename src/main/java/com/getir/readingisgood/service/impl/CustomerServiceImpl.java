package com.getir.readingisgood.service.impl;

import com.getir.readingisgood.auth.domain.request.SignupRequest;
import com.getir.readingisgood.auth.entity.User;
import com.getir.readingisgood.auth.repository.IUserRepository;
import com.getir.readingisgood.auth.service.AuthService;
import com.getir.readingisgood.data.dto.CustomerDTO;
import com.getir.readingisgood.data.entity.Customer;
import com.getir.readingisgood.data.mapper.CustomerMapper;
import com.getir.readingisgood.data.repository.CustomerRepository;
import com.getir.readingisgood.exception.CustomerAlreadyExistsException;
import com.getir.readingisgood.exception.CustomerNotFoundException;
import com.getir.readingisgood.service.ICustomerService;
import com.getir.readingisgood.util.CreateCustomerHelper;
import com.getir.readingisgood.util.PasswordEncoderUtil;
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

    final PasswordEncoderUtil passwordEncoderUtil;

    final CustomerRepository customerRepository;

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        SignupRequest signupRequest = CreateCustomerHelper.mapToSignUpRequest(customerDTO);

        // REGISTER USER WITH AUTH SERVICE
        customerDTO.setPassword(passwordEncoderUtil.encodePassword(customerDTO.getEmail()));
        signupRequest.setPassword(customerDTO.getPassword());
        User user = authService.registerUserAsAdmin(signupRequest);
        Customer customer = customerMapper.toEntity(customerDTO);
        customer.setCustomerId(user.getId());

        // ADD REGISTERED USER TO TABLE IF DOES NOT REGISTERED BEFORE
        getByEmail(customer.getEmail()).ifPresentOrElse(customerDTO1 -> {
            throw new CustomerAlreadyExistsException();
        }, () -> customerDTO.setCustomerId(customerRepository.save(customer).getCustomerId()));

        return customerDTO;
    }

    @Override
    public CustomerDTO findById(Long id) {
        return customerRepository.findById(id).map(customerMapper::toDTO).orElseThrow(CustomerNotFoundException::new);
    }

    @Override
    public Optional<CustomerDTO> getByEmail(String email) {
        return customerRepository.findByEmail(email).map(customerMapper::toDTO);
    }
}
