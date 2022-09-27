package com.getir.readingisgood.service.impl;

import com.getir.readingisgood.data.domain.request.IOrderReport;
import com.getir.readingisgood.data.domain.response.OrderReport;
import com.getir.readingisgood.data.repository.CustomerRepository;
import com.getir.readingisgood.data.repository.OrderRepository;
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

    private final CustomerRepository customerRepository;

    @Override
    public List<OrderReport> getOrderStatisticsForCustomer(Long customerId) {
        customerRepository.findById(customerId).orElseThrow(() -> {throw new RuntimeException("Customer not found");});
        List<IOrderReport> customerOrders = orderRepository.generateReportForCustomer(customerId);
        return helper.createOrderReport(customerOrders);
    }

    @Override
    public List<OrderReport> getOrderStatistics() {
        List<IOrderReport> orderReports = orderRepository.generateReportForAllOrders();
        return helper.createOrderReport(orderReports);
    }
}
