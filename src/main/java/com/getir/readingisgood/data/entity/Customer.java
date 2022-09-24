package com.getir.readingisgood.data.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

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

    @OneToMany(mappedBy = "customer")
    private Set<Order> orders;

}
