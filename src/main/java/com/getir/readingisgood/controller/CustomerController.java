package com.getir.readingisgood.controller;

import com.getir.readingisgood.controller.base.IBaseController;
import com.getir.readingisgood.data.domain.request.PaginationRequest;
import com.getir.readingisgood.data.domain.response.ErrorResponse;
import com.getir.readingisgood.data.dto.CustomerDTO;
import com.getir.readingisgood.data.dto.OrderDTO;
import com.getir.readingisgood.service.ICustomerService;
import com.getir.readingisgood.service.IOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@Tag(name = "customer", description = "Customer API")
@RequestMapping("/api/customer")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
public class CustomerController implements IBaseController {

    private final ICustomerService customerService;
    private final IOrderService orderService;

    @GetMapping("/{id}")
    @Operation(summary = "Find customer", description = "Find customer", tags = "customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = CustomerDTO.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<CustomerDTO> findById(@PathVariable @Parameter(name = "id", description = "customer id") Long id) {
        return ResponseEntity.ok(customerService.findById(id));
    }

    @PostMapping("/create")
    @Operation(summary = "Create customer", description = "Create customer", tags = "customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = CustomerDTO.class))),
            @ApiResponse(responseCode = "409", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody @Valid CustomerDTO customerDTO) {
        return new ResponseEntity<>(customerService.createCustomer(customerDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}/orders")
    @Operation(summary = "Find customer orders", description = "Find customer orders", tags = "customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Page<OrderDTO>> findAllOrdersById(
            @PathVariable @Parameter(name = "customer id") Long id,
            @Valid @Parameter(name = "pagination request", schema = @Schema(implementation = PaginationRequest.class)) PaginationRequest paginationRequest
    ) {
        CustomerDTO customerDTO = customerService.findById(id);
        return ResponseEntity.ok(orderService.findAllOrdersByCustomerId(customerDTO.getCustomerId(), paginationRequest));
    }
}
