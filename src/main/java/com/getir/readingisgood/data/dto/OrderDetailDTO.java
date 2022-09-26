package com.getir.readingisgood.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class OrderDetailDTO {
    @JsonIgnore
    @JsonDeserialize
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private Long bookId;
    private BookDTO book;
    private int amount;
    private Double price;
    @JsonIgnore
    private OrderDTO order;
    private Long orderId;
}
