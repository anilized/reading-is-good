package com.getir.readingisgood.service;

import com.getir.readingisgood.auth.service.impl.AuthServiceImpl;
import com.getir.readingisgood.data.dto.CustomerDTO;
import com.getir.readingisgood.data.entity.Customer;
import com.getir.readingisgood.data.mapper.CustomerMapper;
import com.getir.readingisgood.data.repository.CustomerRepository;
import com.getir.readingisgood.exception.CustomerAlreadyExistsException;
import com.getir.readingisgood.exception.CustomerNotFoundException;
import com.getir.readingisgood.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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

    private static CustomerDTO customerDTO;
    private static Customer customer;

    @BeforeEach
    public void init() {
        customerDTO = CustomerDTO.builder()
                .email("test@test.com")
                .name("test")
                .surname("test")
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
        assertThrows(CustomerAlreadyExistsException.class, () -> customerService.createCustomer(customerDTO));
    }

    @Test
    public void persistConsumer_whenEmailNotExistsAndParametersFullyFilled_thenReturnSuccessResponse() {
        when(customerRepository.findByEmail(customerDTO.getEmail()))
                .thenReturn(Optional.empty());
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
