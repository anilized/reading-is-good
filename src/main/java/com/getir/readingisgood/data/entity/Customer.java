package com.getir.readingisgood.data.entity;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Builder
@Table(name = "customer")
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private Long customerId;

    private String name;
    private String surname;

    private String email;
    private String password;

}
