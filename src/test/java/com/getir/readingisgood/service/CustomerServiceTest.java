package com.getir.readingisgood.service;

import com.getir.readingisgood.auth.entity.User;
import com.getir.readingisgood.auth.service.impl.AuthServiceImpl;
import com.getir.readingisgood.data.dto.CustomerDTO;
import com.getir.readingisgood.data.entity.Customer;
import com.getir.readingisgood.data.mapper.CustomerMapper;
import com.getir.readingisgood.data.repository.CustomerRepository;
import com.getir.readingisgood.exception.CustomerAlreadyExistsException;
import com.getir.readingisgood.exception.CustomerNotFoundException;
import com.getir.readingisgood.service.impl.CustomerServiceImpl;
import com.getir.readingisgood.util.PasswordEncoderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @InjectMocks
    CustomerServiceImpl customerService;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    AuthServiceImpl authService;

    @Spy
    CustomerMapper customerEntityMapper;

    @Mock
    PasswordEncoderUtil passwordEncoderUtil;

    private static CustomerDTO customerDTO;
    private static Customer customer;

    @BeforeEach
    public void init() {
        customerDTO = CustomerDTO.builder()
                .email("test@test.com")
                .password("test@test.com")
                .name("test")
                .surname("test")
                .customerId(1L)
                .build();

        customer = Customer.builder()
                .email("test2@test.com")
                .name("test2")
                .surname("test2")
                .customerId(1L)
                .password("test2@test.com")
                .build();
    }

    @Test
    public void persistConsumer_whenEmailAlreadyExists_thenThrowCustomerAlreadyException() {
        when(customerRepository.findByEmail(customerDTO.getEmail()))
                .thenReturn(Optional.of(customer));
        when(authService.registerUserAsAdmin(any())).thenReturn(new User());
        assertThrows(CustomerAlreadyExistsException.class, () -> customerService.createCustomer(customerDTO));
    }

    @Test
    public void persistConsumer_whenEmailNotExistsAndParametersFullyFilled_thenReturnSuccessResponse() {
        customerDTO.setCustomerId(1L);
        User user = new User();
        user.setEmail(customerDTO.getEmail());
        user.setName(customerDTO.getName());
        user.setPassword(customerDTO.getPassword());
        user.setId(customerDTO.getCustomerId());
        user.setSurname(customerDTO.getSurname());
        when(customerRepository.findByEmail(customerDTO.getEmail()))
                .thenReturn(Optional.empty());
        when(customerRepository.save(any())).thenReturn(customer);
        when(passwordEncoderUtil.encodePassword(any())).thenReturn("test");
        when(authService.registerUserAsAdmin(any())).thenReturn(user);
        CustomerDTO createdCustomerDTO = customerService.createCustomer(customerDTO);
        assertNotNull(createdCustomerDTO);
        assertEquals(customerDTO.getEmail(), createdCustomerDTO.getEmail());
    }

    @Test
    public void getConsumer_whenCustomerNotFound_thenThrowCustomerNotFoundException() {
        when(customerRepository.findById(customerDTO.getCustomerId()))
                .thenReturn(Optional.empty());
        assertThrows(CustomerNotFoundException.class, () -> customerService.findById(customerDTO.getCustomerId()));
    }

}
