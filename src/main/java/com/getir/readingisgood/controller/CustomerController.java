package com.getir.readingisgood.controller;
import com.getir.readingisgood.controller.base.IBaseController;
import com.getir.readingisgood.data.domain.request.PaginationRequest;
import com.getir.readingisgood.data.dto.CustomerDTO;
import com.getir.readingisgood.data.dto.OrderDTO;
import com.getir.readingisgood.service.ICustomerService;
import com.getir.readingisgood.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/customer")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
public class CustomerController implements IBaseController {

    private final ICustomerService customerService;
    private final IOrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.findById(id));
    }
    @PostMapping("/create")
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
        return new ResponseEntity<>(customerService.createCustomer(customerDTO), HttpStatus.CREATED);
    }
    @GetMapping("/{id}/orders")
    public ResponseEntity<Page<OrderDTO>> findAllOrdersById(@PathVariable Long id, @Valid PaginationRequest paginationRequest) {
        CustomerDTO customerDTO = customerService.findById(id);
        return ResponseEntity.ok(orderService.findAllOrdersByCustomerId(customerDTO.getCustomerId(), paginationRequest));
    }
}
