package com.getir.readingisgood.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

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
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @EqualsAndHashCode.Include
    @NotNull
    @JsonDeserialize
    @OneToOne
    @JoinColumn(name = "bookId")
    private Book book;
    @Min(1)
    private int amount;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "orderId")
    @JsonIgnore
    private Order order;
}
