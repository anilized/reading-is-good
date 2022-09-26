package com.getir.readingisgood.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class OrderDetailDTO {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;
    private Long bookId;
    private BookDTO book;
    private int amount;
    private Double price;
    @JsonIgnore
    private OrderDTO order;
    @JsonIgnore
    private Long orderId;
}
