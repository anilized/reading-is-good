package com.getir.readingisgood.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@Table(name = "book")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Book implements Serializable {

    private static final long serialVersionUID = -1351839648270983345L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private Long bookId;

    private String name;
    private String authorName;

    private int stock;
    private double price;

}
