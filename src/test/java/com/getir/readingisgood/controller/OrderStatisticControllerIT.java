package com.getir.readingisgood.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getir.readingisgood.data.domain.response.OrderReport;
import com.getir.readingisgood.service.IOrderStatisticService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Rollback(value = true)
@ActiveProfiles("test")
class OrderStatisticControllerIT {

    @MockBean
    IOrderStatisticService orderStatisticService;

    @Autowired
    private WebApplicationContext context;

    private OrderReport orderReport;

    private MockMvc mockMvc;
    private List<OrderReport> orderReportList;

    @BeforeEach
    void setup() {
        orderReportList = new ArrayList<>();
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        orderReport = new OrderReport();
        orderReport.setTotalBookCount(10);
        orderReport.setTotalPrice(10.5);
        orderReport.setMonth("SEPTEMBER");
        orderReport.setTotalOrderCount(10);
        orderReport.setMonthIndex(9);
        orderReportList.add(orderReport);
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void getUserOrderStatistics_givenCustomerId_thenReturnMonthlyReportList() throws Exception {
        when(orderStatisticService.getOrderStatisticsForCustomer(1L)).thenReturn(orderReportList);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/statistics/{id}", "1")
                        .content(asJsonString(orderReportList))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        verify(orderStatisticService).getOrderStatisticsForCustomer(1L);
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void getOrderStatistics_thenReturnMonthlyReportList() throws Exception {
        when(orderStatisticService.getOrderStatistics()).thenReturn(orderReportList);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/statistics")
                        .content(asJsonString(orderReportList))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        verify(orderStatisticService).getOrderStatistics();
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
