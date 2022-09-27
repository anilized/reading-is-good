package com.getir.readingisgood.service;

import com.getir.readingisgood.data.domain.request.DateIntervalRequest;
import com.getir.readingisgood.data.domain.request.PaginationRequest;
import com.getir.readingisgood.data.dto.CustomerDTO;
import com.getir.readingisgood.data.dto.OrderDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IOrderService {
    OrderDTO findOrderById(Long id);
    Page<OrderDTO> findAllOrdersByCustomerId(Long customerId, PaginationRequest paginationRequest);
    Page<OrderDTO> findAllOrdersWithDateInterval(DateIntervalRequest dateIntervalRequest, PaginationRequest paginationRequest);
    OrderDTO createOrder(OrderDTO orderDTO, CustomerDTO customerDTO);
}
