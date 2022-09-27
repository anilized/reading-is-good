package com.getir.readingisgood.util;

import com.getir.readingisgood.data.domain.request.IOrderReport;
import com.getir.readingisgood.data.domain.response.OrderReport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectionToResponseHelper {
    public static OrderReport toOrderReport(IOrderReport orderReportProjection) {
        OrderReport report = new OrderReport();
        report.setMonthIndex(orderReportProjection.getMonthNum());
        report.setTotalOrderCount(orderReportProjection.getOrderCount());
        report.setTotalPrice(orderReportProjection.getTotalPrice());
        report.setTotalBookCount(orderReportProjection.getBookAmount());
        report.setMonth(report.getMonth());
        return report;
    }

    public List<OrderReport> createOrderReport(List<IOrderReport> orderReportProjectionList) {
        return orderReportProjectionList.stream().map(ProjectionToResponseHelper::toOrderReport).collect(Collectors.toList());
    }

}
