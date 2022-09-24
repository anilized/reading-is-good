package com.getir.readingisgood.data.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@Table
@Entity
public class Customer {
    @Id
    private Long customerId;

    private String name;
    private String surname;

    private String email;
    private String password;

}
