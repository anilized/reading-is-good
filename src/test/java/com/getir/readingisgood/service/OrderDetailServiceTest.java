package com.getir.readingisgood.service;
import com.getir.readingisgood.data.dto.BookDTO;
import com.getir.readingisgood.data.dto.OrderDetailDTO;
import com.getir.readingisgood.service.impl.BookServiceImpl;
import com.getir.readingisgood.service.impl.OrderDetailServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
@ExtendWith(MockitoExtension.class)
public class OrderDetailServiceTest {

    @InjectMocks
    OrderDetailServiceImpl orderDetailService;

    @Mock
    BookServiceImpl bookService;

    private static OrderDetailDTO orderDetailDTO;

    @BeforeEach
    public void setup() {
        orderDetailDTO = new OrderDetailDTO();
        orderDetailDTO.setAmount(2);
        orderDetailDTO.setBookId(1L);
    }

    @Test
    public void createOrder_whenNoExceptionOccurs_thenReturnResponse() {
        BookDTO bookDTO = BookDTO.builder()
                .bookId(orderDetailDTO.getBookId())
                .name("Book A")
                .stock(5)
                .price(123.123).build();

        when(bookService.findById(orderDetailDTO.getBookId()))
                .thenReturn(bookDTO);
        assertEquals(2 * 123.123, orderDetailService.createOrderDetail(orderDetailDTO).getPrice());
    }

}
