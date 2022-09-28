package com.getir.readingisgood.service.impl;

import com.getir.readingisgood.data.domain.request.OrderDetailRequest;
import com.getir.readingisgood.data.dto.BookDTO;
import com.getir.readingisgood.data.dto.OrderDetailDTO;
import com.getir.readingisgood.exception.StockModifiedException;
import com.getir.readingisgood.service.IBookService;
import com.getir.readingisgood.service.IOrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements IOrderDetailService {

    private final IBookService bookService;

    @Retryable(value = StockModifiedException.class, maxAttempts = 4, backoff = @Backoff(delay = 3000, multiplier = 2, maxDelay = 9000))
    @Override
    public OrderDetailDTO createOrderDetail(OrderDetailRequest orderDetailRequest) {
        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
        orderDetailDTO.setAmount(orderDetailRequest.getAmount());
        orderDetailDTO.setBookId(orderDetailRequest.getBookId());
        BookDTO bookDTO = bookService.findById(orderDetailRequest.getBookId());
        isStockAvailable(bookDTO, orderDetailRequest);
        calculatePrice(bookDTO, orderDetailDTO, orderDetailRequest.getAmount());
        sellBook(bookDTO, orderDetailRequest);
        return orderDetailDTO;
    }


    private void sellBook(BookDTO bookDTO, OrderDetailRequest orderDetailRequest) {
        bookService.updateBookStock(bookDTO, bookDTO.getStock() - orderDetailRequest.getAmount());
    }

    private boolean isStockAvailable(BookDTO bookDTO, OrderDetailRequest orderDetailRequest) {
        return bookService.isStockAvailable(bookDTO, orderDetailRequest.getAmount());
    }

    private void calculatePrice(BookDTO bookDTO, OrderDetailDTO orderDetailDTO1, int amount) {
        orderDetailDTO1.setPrice(bookDTO.getPrice() * amount);
        orderDetailDTO1.setBook(bookDTO);
        orderDetailDTO1.setBookId(bookDTO.getBookId());
    }
}
