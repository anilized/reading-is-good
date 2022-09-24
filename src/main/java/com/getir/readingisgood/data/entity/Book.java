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
public class Book {

    @Id
    private Long bookId;

    private String name;
    private String authorName;

    private int stock;
    private double price;

}
