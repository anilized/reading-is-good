package com.getir.readingisgood.data.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrderDetail implements Serializable {

    private Long bookId;
    private int amount;
    private Double price;
}
