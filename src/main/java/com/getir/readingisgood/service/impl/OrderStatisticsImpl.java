package com.getir.readingisgood.service.impl;

import com.getir.readingisgood.data.domain.request.IOrderReport;
import com.getir.readingisgood.data.domain.response.OrderReport;
import com.getir.readingisgood.data.repository.OrderRepository;
import com.getir.readingisgood.exception.CustomerHasNoOrderException;
import com.getir.readingisgood.service.IOrderStatisticService;
import com.getir.readingisgood.util.ProjectionToResponseHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class OrderStatisticsImpl implements IOrderStatisticService {

    private final OrderRepository orderRepository;
    private final ProjectionToResponseHelper helper;

    @Override
    public List<OrderReport> getOrderStatisticsForCustomer(Long customerId) {
        List<IOrderReport> customerOrders;
        customerOrders = orderRepository.generateReportForCustomer(customerId);
        if(customerOrders.size() == 0 || null == customerOrders) {
            throw new CustomerHasNoOrderException();
        } else {
            return helper.createOrderReport(customerOrders);
        }
    }

    @Override
    public List<OrderReport> getOrderStatistics() {
        List<IOrderReport> orderReports = orderRepository.generateReportForAllOrders();
        return helper.createOrderReport(orderReports);
    }
}
