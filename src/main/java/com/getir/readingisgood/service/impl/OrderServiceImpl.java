package com.getir.readingisgood.service.impl;

import com.getir.readingisgood.data.constants.OrderStatusEnum;
import com.getir.readingisgood.data.domain.request.DateIntervalRequest;
import com.getir.readingisgood.data.domain.request.PaginationRequest;
import com.getir.readingisgood.data.dto.CustomerDTO;
import com.getir.readingisgood.data.dto.OrderDTO;
import com.getir.readingisgood.data.entity.Order;
import com.getir.readingisgood.data.entity.OrderDetail;
import com.getir.readingisgood.data.mapper.OrderDetailMapper;
import com.getir.readingisgood.data.mapper.OrderMapper;
import com.getir.readingisgood.data.repository.OrderRepository;
import com.getir.readingisgood.exception.OrderNotFoundException;
import com.getir.readingisgood.service.IOrderDetailService;
import com.getir.readingisgood.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    private final OrderDetailMapper orderDetailMapper;
    private final IOrderDetailService orderDetailService;

    @Override
    public OrderDTO findOrderById(Long id) {
        return orderRepository.findById(id)
                .map(orderMapper::toDTO)
                .orElseThrow(OrderNotFoundException::new);
    }

    @Override
    public Page<OrderDTO> findAllOrdersByCustomerId(Long customerId, PaginationRequest paginationRequest) {
        return orderRepository.findAllByCustomer(customerId,
                PageRequest.of(paginationRequest.getPage(), paginationRequest.getSize()))
                .map(orderMapper::toDTO);
    }

    @Override
    public Page<OrderDTO> findAllOrdersWithDateInterval(DateIntervalRequest dateIntervalRequest, PaginationRequest paginationRequest) {
        return orderRepository.findAllByCreateDateBetween(dateIntervalRequest.getStartDate(),
                dateIntervalRequest.getEndDate(),
                PageRequest.of(paginationRequest.getPage(), paginationRequest.getSize()))
                .map(orderMapper::toDTO);
    }

    @Transactional
    @Override
    public OrderDTO createOrder(OrderDTO orderDTO, CustomerDTO customerDTO) {
        orderDTO.getOrderDetailSet().stream().forEach(orderDetailService::createOrderDetail);
        Set<OrderDetail> orderDetailSet = orderDetailMapper.toEntitySet(orderDTO.getOrderDetailSet());
        orderDTO.setCustomer(customerDTO);
        orderDTO.setStatus(OrderStatusEnum.PENDING.getStatus());
        orderDTO.setCreateDate(new Date());
        Order order = orderMapper.toEntity(orderDTO);
        for(OrderDetail orderDetail : orderDetailSet) {
            orderDetail.setOrder(order);
        }
        order.setOrderDetails(orderDetailSet);
        order = orderRepository.save(order);
        orderDTO.setOrderId(order.getId());
        return orderDTO;
    }
}
