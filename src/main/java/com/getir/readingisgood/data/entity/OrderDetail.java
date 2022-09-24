package com.getir.readingisgood.data.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;

@Data
@Builder
@Entity
@Table(name = "order_detail")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrderDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long bookId;

    @EqualsAndHashCode.Include
    @NotNull
    @JsonDeserialize
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bookId", referencedColumnName = "bookId", insertable = false, updatable = false)
    private Book book;
    @Min(1)
    private int amount;
    @Null
    private Double price;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "orderId", referencedColumnName = "id")
    private Order order;
}
