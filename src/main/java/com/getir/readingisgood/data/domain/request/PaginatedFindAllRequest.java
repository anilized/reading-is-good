package com.getir.readingisgood.data.domain.request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedFindAllRequest {
    @Valid
    private DateIntervalRequest dateIntervalRequest;
    @Valid
    private PaginationRequest paginationRequest;
}
