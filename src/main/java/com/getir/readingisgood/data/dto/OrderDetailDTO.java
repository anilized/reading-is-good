package com.getir.readingisgood.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;
    @NotNull(message = "book id must be given")
    @Schema(description = "Purchased book id", example = "1")
    private Long bookId;
    private BookDTO book;
    @Min(value = 1, message = "amount must be greater than 0")
    @Schema(description = "Purchased amount of book", example = "3")
    private int amount;
    private double price;
    @JsonIgnore
    private OrderDTO order;
    @JsonIgnore
    private Long orderId;
}
