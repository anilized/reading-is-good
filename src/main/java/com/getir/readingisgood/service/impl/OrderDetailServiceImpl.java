package com.getir.readingisgood.service.impl;

import com.getir.readingisgood.data.dto.BookDTO;
import com.getir.readingisgood.data.dto.OrderDetailDTO;
import com.getir.readingisgood.exception.NoAvailableStockException;
import com.getir.readingisgood.exception.StockModifiedException;
import com.getir.readingisgood.service.IBookService;
import com.getir.readingisgood.service.IOrderDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderDetailServiceImpl implements IOrderDetailService {

    private final IBookService bookService;

    @Retryable(value = StockModifiedException.class, maxAttempts = 4, backoff = @Backoff(delay = 3000, multiplier = 2, maxDelay = 9000))
    @Override
    public OrderDetailDTO createOrderDetail(OrderDetailDTO orderDetailDTO) {
        log.info("createOrderDetail started");
        BookDTO bookDTO = bookService.findById(orderDetailDTO.getBookId());
        isStockAvailable(bookDTO, orderDetailDTO);
        calculatePrice(bookDTO, orderDetailDTO);
        sellBook(bookDTO, orderDetailDTO);
        return orderDetailDTO;
    }


    private void sellBook(BookDTO bookDTO, OrderDetailDTO orderDetailDTO) {
        bookService.updateBookStock(bookDTO, bookDTO.getStock() - orderDetailDTO.getAmount());
    }

    private boolean isStockAvailable(BookDTO bookDTO, OrderDetailDTO orderDetailDTO) {
        return bookService.isStockAvailable(bookDTO, orderDetailDTO.getAmount());
    }

    private void calculatePrice(BookDTO bookDTO, OrderDetailDTO orderDetailDTO) {
        orderDetailDTO.setPrice(bookDTO.getPrice() * orderDetailDTO.getAmount());
        orderDetailDTO.setBook(bookDTO);
        orderDetailDTO.setBookId(bookDTO.getBookId());
    }
}
