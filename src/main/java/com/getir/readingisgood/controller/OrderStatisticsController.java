package com.getir.readingisgood.controller;

import com.getir.readingisgood.controller.base.IBaseController;
import com.getir.readingisgood.data.domain.response.OrderReport;
import com.getir.readingisgood.service.IOrderStatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/statistics")
public class OrderStatisticsController implements IBaseController {

    private final IOrderStatisticService orderStatisticService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<List<OrderReport>> getUserOrderStatistics(@PathVariable(name = "id") Long customerId) {
        return ResponseEntity.ok(orderStatisticService.getOrderStatisticsForCustomer(customerId));
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<OrderReport>> getStatisticsForAllOrders() {
        return ResponseEntity.ok(orderStatisticService.getOrderStatistics());
    }

}
