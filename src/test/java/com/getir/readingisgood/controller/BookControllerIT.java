package com.getir.readingisgood.controller;

import com.getir.readingisgood.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Rollback(value = true)
@ActiveProfiles("test")
public class BookControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IBookService bookService;

}
