package com.getir.readingisgood.controller;

import com.getir.readingisgood.auth.service.impl.UserDetailsImpl;
import com.getir.readingisgood.controller.base.IBaseController;
import com.getir.readingisgood.data.domain.request.DateIntervalRequest;
import com.getir.readingisgood.data.domain.request.PaginationRequest;
import com.getir.readingisgood.data.dto.CustomerDTO;
import com.getir.readingisgood.data.dto.OrderDTO;
import com.getir.readingisgood.service.ICustomerService;
import com.getir.readingisgood.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController implements IBaseController {

    private final IOrderService orderService;
    private final ICustomerService customerService;
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PostMapping(consumes = "application/json")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody @Valid OrderDTO orderDTO, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CustomerDTO customerDTO = customerService.getByEmail(userDetails.getEmail()).get();
        return new ResponseEntity<>(orderService.createOrder(orderDTO, customerDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> findOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findOrderById(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/orders-between-dates")
    public ResponseEntity<Page<OrderDTO>> findAllOrdersBetweenDates(@RequestBody @Valid DateIntervalRequest dateIntervalRequest, @Valid PaginationRequest paginationRequest) {
        return ResponseEntity.ok(orderService.findAllOrdersWithDateInterval(dateIntervalRequest, paginationRequest));
    }

}
