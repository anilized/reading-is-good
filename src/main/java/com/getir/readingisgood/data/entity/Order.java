package com.getir.readingisgood.data.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@Builder
@Table(name = "order_table")
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "customerId", nullable = false)
    private Customer customer;

    private String status;
    private Date createDate;
    @OneToOne(mappedBy = "order")
    private OrderDetail orderDetails;

}
