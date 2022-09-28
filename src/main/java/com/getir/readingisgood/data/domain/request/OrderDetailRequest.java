package com.getir.readingisgood.data.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailRequest {
    @NotNull
    private Long bookId;
    @Min(1)
    private int amount;
}
