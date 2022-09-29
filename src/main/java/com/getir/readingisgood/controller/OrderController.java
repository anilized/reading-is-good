package com.getir.readingisgood.controller;

import com.getir.readingisgood.data.domain.request.CreateOrderRequest;
import com.getir.readingisgood.data.domain.request.PaginatedFindAllRequest;
import com.getir.readingisgood.auth.service.impl.UserDetailsImpl;
import com.getir.readingisgood.controller.base.IBaseController;
import com.getir.readingisgood.data.domain.response.ErrorResponse;
import com.getir.readingisgood.data.dto.CustomerDTO;
import com.getir.readingisgood.data.dto.OrderDTO;
import com.getir.readingisgood.service.ICustomerService;
import com.getir.readingisgood.service.IOrderService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Tag(name = "order", description = "Order API")
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController implements IBaseController {

    private final IOrderService orderService;
    private final ICustomerService customerService;
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PostMapping(consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = OrderDTO.class))),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<OrderDTO> createOrder(@RequestBody @Valid CreateOrderRequest createOrderRequest, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CustomerDTO customerDTO = customerService.getByEmail(userDetails.getEmail()).get();
        return new ResponseEntity<>(orderService.createOrder(createOrderRequest, customerDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = OrderDTO.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<OrderDTO> findOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findOrderById(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/orders-between-dates", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Page<OrderDTO>> findAllOrdersBetweenDates(@RequestBody @Valid PaginatedFindAllRequest paginatedFindAllRequest) {
        return ResponseEntity.ok(orderService.findAllOrdersWithDateInterval(paginatedFindAllRequest));
    }

}
