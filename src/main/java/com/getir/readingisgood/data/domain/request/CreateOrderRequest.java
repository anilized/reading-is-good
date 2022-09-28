package com.getir.readingisgood.data.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {
    private Set<OrderDetailRequest> orderDetailSet;
}
