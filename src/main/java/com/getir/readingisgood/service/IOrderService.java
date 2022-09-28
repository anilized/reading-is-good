package com.getir.readingisgood.service;

import com.getir.readingisgood.data.domain.request.CreateOrderRequest;
import com.getir.readingisgood.data.domain.request.PaginatedFindAllRequest;
import com.getir.readingisgood.data.domain.request.PaginationRequest;
import com.getir.readingisgood.data.dto.CustomerDTO;
import com.getir.readingisgood.data.dto.OrderDTO;
import org.springframework.data.domain.Page;

public interface IOrderService {
    OrderDTO findOrderById(Long id);
    Page<OrderDTO> findAllOrdersByCustomerId(Long customerId, PaginationRequest paginationRequest);
    Page<OrderDTO> findAllOrdersWithDateInterval(PaginatedFindAllRequest paginatedFindAllRequest);
    OrderDTO createOrder(CreateOrderRequest createOrderRequest, CustomerDTO customerDTO);
}
