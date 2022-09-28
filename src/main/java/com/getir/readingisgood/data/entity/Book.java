package com.getir.readingisgood.data.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
@Data
@Builder
@Table(name = "book")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private Long bookId;
    @Schema(description = "Name of the book", example = "The Lord of the Rings: Return of the King")
    private String name;
    @Schema(description = "Name of the author", example = "J.R.R. Tolkien")
    private String authorName;
    @Schema(description = "Stock of the book", example = "3")
    private int stock;
    @Schema(description = "Price of the book", example = "29.99")
    private double price;
    @Version // Optimistic Locking
    private Long version;

}
