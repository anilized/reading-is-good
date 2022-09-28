package com.getir.readingisgood.service;

import com.getir.readingisgood.data.domain.request.IOrderReport;
import com.getir.readingisgood.data.domain.response.OrderReport;
import com.getir.readingisgood.data.repository.OrderRepository;
import com.getir.readingisgood.exception.CustomerHasNoOrderException;
import com.getir.readingisgood.exception.OrderNotFoundException;
import com.getir.readingisgood.service.impl.OrderStatisticsImpl;
import com.getir.readingisgood.util.ProjectionToResponseHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderStatisticsTest {

    @InjectMocks
    OrderStatisticsImpl orderStatistics;

    @Mock
    OrderRepository orderRepository;

    @Mock
    ProjectionToResponseHelper helper;

    private static OrderReport orderReport;

    @BeforeEach
    public void setup() {
        orderReport = new OrderReport();
        orderReport.setMonth("SEPTEMBER");
        orderReport.setMonthIndex(9);
        orderReport.setTotalOrderCount(1);
        orderReport.setTotalPrice(100.20);
        orderReport.setTotalBookCount(3);
    }

    @Test
    public void getOrderStatisticsForCustomer_whenNoExceptionOccurs_thenOK() {
        List<IOrderReport> customerOrders = new ArrayList<>();
        IOrderReport orderReport1 = new IOrderReport() {
            @Override
            public Integer getMonthNum() {
                return 9;
            }

            @Override
            public Integer getOrderCount() {
                return 1;
            }

            @Override
            public Integer getBookAmount() {
                return 3;
            }

            @Override
            public Double getTotalPrice() {
                return 10.20;
            }
        };
        customerOrders.add(orderReport1);
        List<OrderReport> orderReports = new ArrayList<>();
        orderReports.add(orderReport);
        when(orderRepository.generateReportForCustomer(1L)).thenReturn(customerOrders);
        when(helper.createOrderReport(customerOrders)).thenReturn(orderReports);
        when(orderStatistics.getOrderStatisticsForCustomer(1L)).thenReturn(orderReports);
        assertEquals(orderReport.getTotalPrice(), orderStatistics.getOrderStatisticsForCustomer(1L).get(0).getTotalPrice());
        assertEquals(orderReport.getMonth(), orderStatistics.getOrderStatisticsForCustomer(1L).get(0).getMonth());
        assertEquals(orderReport.getTotalBookCount(), orderStatistics.getOrderStatisticsForCustomer(1L).get(0).getTotalBookCount());
    }

    @Test
    public void getOrderStatisticsForCustomer_whenCustomerHasNoOrder_thenThrowException() {
        List<IOrderReport> emptyList = new ArrayList<>();
        when(orderRepository.generateReportForCustomer(1L)).thenReturn(emptyList);
        assertThrows(CustomerHasNoOrderException.class, () -> orderStatistics.getOrderStatisticsForCustomer(1L));
    }

    @Test
    public void getOrderStatistics_whenOrderFound_thenOK() {
        List<IOrderReport> customerOrders = new ArrayList<>();
        IOrderReport orderReport1 = new IOrderReport() {
            @Override
            public Integer getMonthNum() {
                return 9;
            }

            @Override
            public Integer getOrderCount() {
                return 1;
            }

            @Override
            public Integer getBookAmount() {
                return 3;
            }

            @Override
            public Double getTotalPrice() {
                return 10.20;
            }
        };
        customerOrders.add(orderReport1);
        List<OrderReport> orderReports = new ArrayList<>();
        orderReports.add(orderReport);
        when(orderRepository.generateReportForAllOrders()).thenReturn(customerOrders);
        when(helper.createOrderReport(customerOrders)).thenReturn(orderReports);
        when(orderStatistics.getOrderStatistics()).thenReturn(orderReports);
        assertEquals(orderReport.getTotalPrice(), orderStatistics.getOrderStatistics().get(0).getTotalPrice());
        assertEquals(orderReport.getMonth(), orderStatistics.getOrderStatistics().get(0).getMonth());
        assertEquals(orderReport.getTotalBookCount(), orderStatistics.getOrderStatistics().get(0).getTotalBookCount());
    }

    @Test
    public void getOrderStatistics_whenNoOrderFound_thenThrowException() {
        List<IOrderReport> emptyList = new ArrayList<>();
        when(orderRepository.generateReportForAllOrders()).thenReturn(emptyList);
        assertThrows(OrderNotFoundException.class, () -> orderStatistics.getOrderStatistics());
    }

}
