package com.getir.readingisgood.data.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Builder
@Table(name = "book")
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private Long bookId;

    private String name;
    private String authorName;

    private int stock;
    private double price;

}
