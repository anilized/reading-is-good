package com.getir.readingisgood.service;

import com.getir.readingisgood.data.domain.response.OrderReport;

import java.util.List;

public interface IOrderStatisticService {
    List<OrderReport> getOrderStatisticsForCustomer(Long customerId);
    List<OrderReport> getOrderStatistics();
}
