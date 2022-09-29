package com.getir.readingisgood.controller;

import com.getir.readingisgood.controller.base.IBaseController;
import com.getir.readingisgood.data.domain.response.ErrorResponse;
import com.getir.readingisgood.data.domain.response.OrderReport;
import com.getir.readingisgood.service.IOrderStatisticService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "order-statistics", description = "Order Statistics API")
@RequestMapping("/api/statistics")
public class OrderStatisticsController implements IBaseController {

    private final IOrderStatisticService orderStatisticService;

    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = OrderReport.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<List<OrderReport>> getUserOrderStatistics(@PathVariable(name = "id") Long customerId) {
        return ResponseEntity.ok(orderStatisticService.getOrderStatisticsForCustomer(customerId));
    }

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = OrderReport.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    public ResponseEntity<List<OrderReport>> getStatisticsForAllOrders() {
        return ResponseEntity.ok(orderStatisticService.getOrderStatistics());
    }

}
