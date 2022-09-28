package com.getir.readingisgood.service;

import com.getir.readingisgood.data.domain.request.OrderDetailRequest;
import com.getir.readingisgood.data.dto.OrderDetailDTO;
public interface IOrderDetailService {
    OrderDetailDTO createOrderDetail(OrderDetailRequest orderDetailRequest);
}
