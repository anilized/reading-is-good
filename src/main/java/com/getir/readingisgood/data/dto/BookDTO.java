package com.getir.readingisgood.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import javax.validation.constraints.*;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO implements Serializable {

    @JsonDeserialize
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long bookId;

    @NotBlank(message = "Book name must be provided")
    private String name;
    @NotBlank(message = "Author name must be provided")
    private String authorName;
    @NotNull(message = "Stock must be provided")
    @Min(value = 1, message = "Stock must be greater than 0")
    private int stock;
    @NotNull(message = "Price must be provided")
    @DecimalMin(value = "1.0", message = "Price must be greater than 0")
    private double price;
    @JsonIgnore
    private Long version;


}
