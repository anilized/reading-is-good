package com.getir.readingisgood.mapper;

import com.getir.readingisgood.data.dto.CustomerDTO;
import com.getir.readingisgood.data.dto.OrderDTO;
import com.getir.readingisgood.data.entity.Customer;
import com.getir.readingisgood.data.entity.Order;
import com.getir.readingisgood.data.mapper.OrderMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class OrderMapperTest {

    OrderMapper orderMapper;

    Order order;

    OrderDTO orderDTO;

    @BeforeEach
    void setup() {
        orderMapper = new OrderMapper();
        order = new Order();
        order.setOrderDetails(new HashSet<>());
        order.setId(1L);
        order.setStatus("PENDING");
        order.setCreateDate(new Date());
        order.setCustomer(new Customer());

        orderDTO = new OrderDTO();
        orderDTO.setOrderId(1L);
        orderDTO.setOrderDetailSet(new HashSet<>());
        orderDTO.setCreateDate(new Date());
        orderDTO.setStatus("PENDING");
        orderDTO.setCustomer(new CustomerDTO());
    }

    @Test
    void toDto_whenEntityGiven_shouldReturnDto() {
        OrderDTO dto = orderMapper.toDTO(order);
        assertEquals(dto.getOrderId(), orderDTO.getOrderId());
    }

    @Test
    void toEntity_whenDtoGiven_shouldReturnEntity() {
        Order entity = orderMapper.toEntity(orderDTO);
        assertEquals(entity.getId(), order.getId());
    }

}
