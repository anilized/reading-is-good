package com.getir.readingisgood.service.impl;

import com.getir.readingisgood.data.dto.BookDTO;
import com.getir.readingisgood.data.dto.OrderDetailDTO;
import com.getir.readingisgood.service.IBookService;
import com.getir.readingisgood.service.IOrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements IOrderDetailService {

    private final IBookService bookService;
    @Override
    public OrderDetailDTO createOrderDetail(OrderDetailDTO orderDetailDTO) {
        BookDTO bookDTO = bookService.findById(orderDetailDTO.getBookId());
        calculatePrice(bookDTO,orderDetailDTO);
        sellBook(bookDTO, orderDetailDTO);
        return orderDetailDTO;
    }


    private void sellBook(BookDTO bookDTO, OrderDetailDTO orderDetailDTO) {
        if(bookService.isStockAvailable(bookDTO,orderDetailDTO.getAmount())) {
            bookService.updateBookStock(bookDTO, bookDTO.getStock() - orderDetailDTO.getAmount());
        } else {
            //TODO: Create new exception
            throw new RuntimeException("Stock not available");
        }

    }

    private void calculatePrice(BookDTO bookDTO,OrderDetailDTO orderDetailDTO) {
        orderDetailDTO.setPrice(bookDTO.getPrice() * orderDetailDTO.getAmount());
        orderDetailDTO.setBook(bookDTO);
        orderDetailDTO.setBookId(bookDTO.getBookId());
    }
}
