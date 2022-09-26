package com.getir.readingisgood.service;

import com.getir.readingisgood.data.dto.BookDTO;
import com.getir.readingisgood.data.dto.OrderDetailDTO;

import java.util.List;

public interface IOrderDetailService {
    OrderDetailDTO createOrderDetail(OrderDetailDTO orderDetailDTO);
}
