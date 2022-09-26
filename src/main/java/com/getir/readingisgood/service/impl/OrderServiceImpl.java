package com.getir.readingisgood.service.impl;

import com.getir.readingisgood.data.constants.OrderStatusEnum;
import com.getir.readingisgood.data.domain.request.DateIntervalRequest;
import com.getir.readingisgood.data.domain.request.PaginationRequest;
import com.getir.readingisgood.data.dto.CustomerDTO;
import com.getir.readingisgood.data.dto.OrderDTO;
import com.getir.readingisgood.data.entity.Order;
import com.getir.readingisgood.data.mapper.OrderMapper;
import com.getir.readingisgood.data.repository.OrderRepository;
import com.getir.readingisgood.service.IOrderDetailService;
import com.getir.readingisgood.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final IOrderDetailService orderDetailService;

    @Override
    public OrderDTO findOrderById(Long id) {
        return orderRepository.findById(id)
                .map(orderMapper::toDTO)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public Page<OrderDTO> findAllOrdersByCustomerId(Long customerId, PaginationRequest paginationRequest) {
        return null;
    }

    @Override
    public Page<OrderDTO> findAllOrdersWithDateInterval(DateIntervalRequest dateIntervalRequest, PaginationRequest paginationRequest) {
        return null;
    }

    @Transactional
    @Override
    public OrderDTO createOrder(OrderDTO orderDTO, CustomerDTO customerDTO) {
        orderDTO.getOrderDetailSet().stream().forEach(orderDetailService::createOrderDetail);
        orderDTO.setCustomer(customerDTO);
        orderDTO.setStatus(OrderStatusEnum.PENDING.getStatus());
        orderDTO.setCreateDate(new Date());
        Order order = orderRepository.save(orderMapper.toEntity(orderDTO));
        orderDTO.setOrderId(order.getId());
        List<Long> orderDetailIdList = order.getOrderDetails().stream().map(r->r.getId()).collect(Collectors.toList());
        orderDetailService.updateOrderDetails(order.getId(), orderDetailIdList);
        return orderDTO;
    }
}
