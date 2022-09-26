package com.getir.readingisgood.service.impl;

import com.getir.readingisgood.data.dto.BookDTO;
import com.getir.readingisgood.data.dto.OrderDetailDTO;
import com.getir.readingisgood.data.repository.OrderDetailRepository;
import com.getir.readingisgood.service.IBookService;
import com.getir.readingisgood.service.IOrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements IOrderDetailService {

    private final IBookService bookService;
    private final OrderDetailRepository orderDetailRepository;

    @Override
    public OrderDetailDTO createOrderDetail(OrderDetailDTO orderDetailDTO) {
        BookDTO bookDTO = bookService.findById(orderDetailDTO.getBookId());
        orderDetailDTO.setPrice(bookDTO.getPrice() * orderDetailDTO.getAmount());
        orderDetailDTO.setBook(bookDTO);
        orderDetailDTO.setBookId(bookDTO.getBookId());
        return orderDetailDTO;
    }

    @Override
    public int updateOrderDetails(Long orderId, List<Long> idList) {
        int count = 0;
        for(Long id : idList) {
            count+=orderDetailRepository.updateOrderId(orderId,id);
        }
        return count;
    }
}
