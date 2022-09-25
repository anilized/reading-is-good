
package com.getir.readingisgood.data.mapper;

import com.getir.readingisgood.data.dto.CustomerDTO;
import com.getir.readingisgood.data.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerMapper implements Mapper<Customer, CustomerDTO> {



    @Override
    public CustomerDTO toDTO(Customer customer) {
        return CustomerDTO.builder()
                .customerId(customer.getCustomerId())
                .name(customer.getName())
                .surname(customer.getSurname())
                .email(customer.getEmail())
                .password(customer.getPassword())
                .build();
    }

    @Override
    public Customer toEntity(CustomerDTO customerDTO) {
        return Customer.builder()
                .customerId(customerDTO.getCustomerId())
                .email(customerDTO.getEmail())
                .name(customerDTO.getName())
                .password(customerDTO.getPassword())
                .surname(customerDTO.getSurname())
                .build();
    }
}

