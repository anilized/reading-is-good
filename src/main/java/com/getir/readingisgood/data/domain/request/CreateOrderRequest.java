package com.getir.readingisgood.data.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {
    @Valid
    private Set<OrderDetailRequest> orderDetailSet;
}
