package com.getir.readingisgood.data.mapper;

import com.getir.readingisgood.data.dto.OrderDTO;
import com.getir.readingisgood.data.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderMapper implements Mapper<Order, OrderDTO> {

    @Override
    public OrderDTO toDTO(Order order) {
        CustomerMapper customerMapper = new CustomerMapper();
        OrderDetailMapper orderDetailMapper = new OrderDetailMapper();
        return OrderDTO.builder()
                .orderId(order.getId())
                .customer(customerMapper.toDTO(order.getCustomer()))
                .status(order.getStatus())
                .createDate(order.getCreateDate())
                .orderDetailSet(orderDetailMapper.toDTOSet(order.getOrderDetails()))
                .build();
    }

    public Set<OrderDTO> toOrderDTOSet (Set<Order> orders) {
        Set<OrderDTO> orderDTOSet = (Set<OrderDTO>) orders.stream().map(r -> toDTO(r)).collect(Collectors.toList());
        return orderDTOSet;
    }

    @Override
    public Order toEntity(OrderDTO orderDTO) {
        CustomerMapper customerMapper = new CustomerMapper();
        OrderDetailMapper orderDetailMapper = new OrderDetailMapper();
        return Order.builder()
                .customer(customerMapper.toEntity(orderDTO.getCustomer()))
                .status(orderDTO.getStatus())
                .orderDetails(orderDetailMapper.toEntitySet(orderDTO.getOrderDetailSet()))
                .createDate(orderDTO.getCreateDate())
                .build();
    }

    public Set<Order> toOrderSet (Set<OrderDTO> orders) {
        Set<Order> orderSet = (Set<Order>) orders.stream().map(r -> toEntity(r)).collect(Collectors.toList());
        return orderSet;
    }
}
