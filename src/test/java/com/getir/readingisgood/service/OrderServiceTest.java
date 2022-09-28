package com.getir.readingisgood.service;

import com.getir.readingisgood.data.domain.request.PaginatedFindAllRequest;
import com.getir.readingisgood.data.domain.request.DateIntervalRequest;
import com.getir.readingisgood.data.domain.request.PaginationRequest;
import com.getir.readingisgood.data.dto.BookDTO;
import com.getir.readingisgood.data.dto.CustomerDTO;
import com.getir.readingisgood.data.dto.OrderDTO;
import com.getir.readingisgood.data.dto.OrderDetailDTO;
import com.getir.readingisgood.data.mapper.CustomerMapper;
import com.getir.readingisgood.data.mapper.OrderDetailMapper;
import com.getir.readingisgood.data.mapper.OrderMapper;
import com.getir.readingisgood.data.repository.OrderRepository;
import com.getir.readingisgood.exception.OrderNotFoundException;
import com.getir.readingisgood.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @InjectMocks
    OrderServiceImpl orderService;
    @Mock
    OrderRepository orderRepository;
    @Spy
    OrderMapper orderMapper;
    @Spy
    OrderDetailMapper orderDetailMapper;
    @Spy
    CustomerMapper customerMapper;
    @Mock
    IOrderDetailService orderDetailService;

    private static OrderDTO orderDTO;
    private static CustomerDTO customerDTO;
    private static OrderDetailDTO orderDetailDTO;
    private static Set<OrderDetailDTO> orderDetailDTOSet;
    private static PaginationRequest paginationRequest;
    private static DateIntervalRequest dateIntervalRequest;
    private static PaginatedFindAllRequest paginatedFindAllRequest;

    @BeforeEach
    void setup() {
        paginatedFindAllRequest = new PaginatedFindAllRequest();
        dateIntervalRequest = new DateIntervalRequest();
        dateIntervalRequest.setStartDate(new Date());
        dateIntervalRequest.setEndDate(new Date(2022,12,12));
        paginationRequest = new PaginationRequest();
        paginationRequest.setPage(0);
        paginationRequest.setSize(10);
        customerDTO = new CustomerDTO();
        customerDTO.setSurname("can");
        customerDTO.setName("anil");
        customerDTO.setEmail("anillcan7@gmail.com");
        customerDTO.setPassword("anillcan7@gmail.com");
        orderDetailDTO = new OrderDetailDTO();
        orderDetailDTO.setOrder(null);
        orderDetailDTO.setBookId(1L);
        orderDetailDTO.setPrice(100.0);
        orderDetailDTO.setAmount(2);
        orderDetailDTO.setId(1L);
        orderDetailDTO.setBook(new BookDTO(1L, "Test Book", "Test Author", 5, 50, 0L));
        orderDetailDTOSet = new HashSet<>();
        orderDetailDTOSet.add(orderDetailDTO);
        orderDTO = new OrderDTO();
        orderDTO.setOrderId(1L);
        orderDTO.setCustomer(customerDTO);
        orderDTO.setCreateDate(new Date());
        orderDTO.setOrderDetailSet(orderDetailDTOSet);
        paginatedFindAllRequest.setDateIntervalRequest(dateIntervalRequest);
        paginatedFindAllRequest.setPaginationRequest(paginationRequest);
    }

    @Test
    void findOrderById_whenOrderNotExist_thenThrowOrderNotFound() {
        when(orderRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(OrderNotFoundException.class, () -> orderService.findOrderById(1L));
    }

    @Test
    void findAllOrdersByCustomerId_whenNoOrderFoundForCustomer_thenReturnEmptyPage() {
        when(orderRepository.findAllByCustomer(1L,PageRequest.of(paginationRequest.getPage(), paginationRequest.getSize()))).thenReturn(Page.empty());
        when(orderService.findAllOrdersByCustomerId(1L, paginationRequest)).thenReturn(Page.empty());
        assertEquals(Page.empty().getContent().size(), orderService.findAllOrdersByCustomerId(1L, paginationRequest).getContent().size());
    }

    @Test
    void findAllOrdersBetweenDate_whenNoOrderFound_thenReturnEmptyPage() {
        when(orderRepository.findAllByCreateDateBetween(paginatedFindAllRequest.getDateIntervalRequest().getStartDate(),paginatedFindAllRequest.getDateIntervalRequest().getEndDate(),PageRequest.of(paginatedFindAllRequest.getPaginationRequest().getPage(), paginatedFindAllRequest.getPaginationRequest().getSize()))).thenReturn(Page.empty());
        when(orderService.findAllOrdersWithDateInterval(paginatedFindAllRequest)).thenReturn(Page.empty());
        assertEquals(Page.empty().getContent().size(), orderService.findAllOrdersWithDateInterval(paginatedFindAllRequest).getContent().size());
    }


}
