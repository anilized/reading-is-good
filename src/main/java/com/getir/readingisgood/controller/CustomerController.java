package com.getir.readingisgood.controller;
import com.getir.readingisgood.controller.base.IBaseController;
import com.getir.readingisgood.data.dto.CustomerDTO;
import com.getir.readingisgood.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/customer")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
public class CustomerController implements IBaseController {

    final ICustomerService customerService;


    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.findById(id));
    }
    @PostMapping("/create")
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
        return new ResponseEntity<>(customerService.createCustomer(customerDTO), HttpStatus.CREATED);
    }
}
