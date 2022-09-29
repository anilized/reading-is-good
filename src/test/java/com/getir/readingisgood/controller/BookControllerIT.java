package com.getir.readingisgood.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getir.readingisgood.data.dto.BookDTO;
import com.getir.readingisgood.service.IBookService;
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
public class BookControllerIT {

    private MockMvc mockMvc;
    @MockBean
    private IBookService bookService;

    @Autowired
    private WebApplicationContext context;

    private BookDTO bookDTORequest;
    private BookDTO bookDTOResponse;
    private BookDTO bookDTO1;
    private BookDTO bookDTO2;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();

        bookDTO1 = new BookDTO();
        bookDTO1.setName("test book");
        bookDTO1.setStock(2);
        bookDTO1.setPrice(100);
        bookDTO1.setAuthorName("test author");

        bookDTO2 = new BookDTO();
        bookDTO2.setName("test book");
        bookDTO2.setStock(2);
        bookDTO2.setPrice(100);
        bookDTO2.setAuthorName("test author");

        bookDTORequest = new BookDTO();
        bookDTORequest.setBookId(1L);
        bookDTORequest.setName("test book");
        bookDTORequest.setStock(2);
        bookDTORequest.setPrice(100);
        bookDTORequest.setAuthorName("test author");

        bookDTOResponse = new BookDTO();
        bookDTOResponse.setBookId(1L);
        bookDTOResponse.setName("test book");
        bookDTOResponse.setStock(2);
        bookDTOResponse.setPrice(100);
        bookDTOResponse.setAuthorName("test author");
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void createBook_whenValidBookDtoGiven_shouldReturnCreated() throws Exception {
        when(bookService.createBook(bookDTO1)).thenReturn(bookDTO2);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/book")
                        .content(asJsonString(bookDTO1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated()).andReturn();
        verify(bookService).createBook(bookDTO2);
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void updateBook_whenValidBookDtoGiven_shouldReturnOK() throws Exception {
        bookDTOResponse.setStock(3);
        when(bookService.findById(1L)).thenReturn(bookDTORequest);
        when(bookService.updateBookStock(bookDTORequest,3)).thenReturn(bookDTOResponse);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/book/{id}", "1").queryParam("stock", "3")
                        .content(asJsonString(bookDTORequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        verify(bookService).updateBookStock(bookDTORequest,3);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
