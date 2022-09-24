package com.getir.readingisgood.data.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.Set;

@Data
@Builder
@Table
@Entity
public class Order {

    private Long id;

    private Long customerId;

    private String status;
    private Date createDate;
    private Set<OrderDetail> orderDetails;


}
