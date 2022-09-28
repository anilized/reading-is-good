package com.getir.readingisgood.util;

import com.getir.readingisgood.data.domain.request.IOrderReport;
import com.getir.readingisgood.data.domain.response.OrderReport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ProjectionToResponseHelperTest {

    ProjectionToResponseHelper helper;

    private IOrderReport orderReport;
    private List<IOrderReport> iOrderReportList;
    private OrderReport orderReportImpl;
    private List<OrderReport> orderReportList;

    @BeforeEach
    void setup() {
        orderReportList = new ArrayList<>();
        helper = new ProjectionToResponseHelper();
        orderReportImpl = new OrderReport();
        orderReportImpl.setMonthIndex(1);
        orderReportImpl.setTotalOrderCount(1);
        orderReportImpl.setTotalBookCount(1);
        orderReportImpl.setTotalPrice(10.0);
        orderReportImpl.setMonth("JANUARY");
        iOrderReportList = new ArrayList<>();
        orderReport = new IOrderReport() {
            @Override
            public Integer getMonthNum() {
                return 1;
            }

            @Override
            public Integer getOrderCount() {
                return 1;
            }

            @Override
            public Integer getBookAmount() {
                return 1;
            }

            @Override
            public Double getTotalPrice() {
                return 10.0;
            }
        };
        iOrderReportList.add(orderReport);
        orderReportList.add(orderReportImpl);
    }

    @Test
    void toOrderReport_whenIOrderReportGiven_thenReturnReport() {
        OrderReport result = ProjectionToResponseHelper.toOrderReport(orderReport);
        assertEquals(orderReportImpl.getMonth(), result.getMonth());
    }

    @Test
    void createOrderReport_whenIOrderReportListGiven_thenReturnOrderReportList() {
        List<OrderReport> result = helper.createOrderReport(iOrderReportList);
        assertEquals(orderReportList.size(), result.size());
    }
}
