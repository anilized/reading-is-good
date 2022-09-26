package com.getir.readingisgood.service;

import com.getir.readingisgood.data.dto.OrderDetailDTO;

import java.util.List;

public interface IOrderDetailService {
    OrderDetailDTO createOrderDetail(OrderDetailDTO orderDetailDTO);

    int updateOrderDetails(Long orderId,List<Long> id);

}
