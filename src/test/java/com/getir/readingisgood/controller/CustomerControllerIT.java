package com.getir.readingisgood.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getir.readingisgood.data.domain.request.PaginationRequest;
import com.getir.readingisgood.data.dto.CustomerDTO;
import com.getir.readingisgood.service.ICustomerService;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Rollback(value = true)
@ActiveProfiles("test")
public class CustomerControllerIT {

    @MockBean
    ICustomerService customerService;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    private CustomerDTO customerDTO;
    private CustomerDTO customerDTO2;
    private PaginationRequest paginationRequest;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        customerDTO = new CustomerDTO();
        customerDTO.setName("test");
        customerDTO.setSurname("test");
        customerDTO.setEmail("test@gmail.com");

        paginationRequest = new PaginationRequest();
        paginationRequest.setSize(1);
        paginationRequest.setPage(0);

        customerDTO2 = new CustomerDTO();
        customerDTO2.setCustomerId(1L);
        customerDTO2.setName("test");
        customerDTO2.setSurname("test");
        customerDTO2.setEmail("test@gmail.com");
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void createCustomer_whenValidCustomerDTOGiven_shouldReturnCreated() throws Exception {
        when(customerService.createCustomer(customerDTO)).thenReturn(customerDTO);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/customer")
                        .content(asJsonString(customerDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
        verify(customerService).createCustomer(customerDTO);
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void findById_whenCustomerIdGiven_shouldReturnResponse() throws Exception {
        when(customerService.findById(1L)).thenReturn(customerDTO2);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/customer/{id}", "1")
                        .content(asJsonString(customerDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        verify(customerService).findById(1L);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}


