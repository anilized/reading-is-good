package com.getir.readingisgood.service;

import com.getir.readingisgood.data.dto.BookDTO;
import com.getir.readingisgood.data.entity.Book;
import com.getir.readingisgood.data.mapper.BookMapper;
import com.getir.readingisgood.data.repository.BookRepository;
import com.getir.readingisgood.exception.BookNotFoundException;
import com.getir.readingisgood.exception.NoAvailableStockException;
import com.getir.readingisgood.exception.StockModifiedException;
import com.getir.readingisgood.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.OptimisticLockingFailureException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {


    @InjectMocks
    BookServiceImpl bookService;

    @Mock
    BookRepository bookRepository;

    @Spy
    BookMapper mapper;

    private static BookDTO bookDTO;
    private static Book book;

    @BeforeEach
    public void setup() {
        bookDTO = BookDTO.builder()
                .bookId(1L)
                .name("Test Book")
                .authorName("Test Author")
                .stock(10)
                .price(25.99)
                .build();

        book = Book.builder()
                .bookId(1L)
                .name("Test Book")
                .authorName("Test Author")
                .stock(10)
                .price(25.99)
                .version(0L)
                .build();
    }

    @Test
    void findBook_whenBookNotFound_thenThrowBookNotFound() {
        when(bookRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class, () -> bookService.findById(1L));
    }

    @Test
    public void isStockAvailable_whenStockNotEnough_thenThrowNoAvailableStock() {
        assertThrows(NoAvailableStockException.class, () -> bookService.isStockAvailable(bookDTO, bookDTO.getStock()+1));
    }

    @Test
    public void updateStock_whenOptimisticFailureOccured_thenThrowStockModified() {
        when(bookRepository.save(any())).thenThrow(OptimisticLockingFailureException.class);
        assertThrows(StockModifiedException.class, () -> bookService.updateBookStock(bookDTO, 5));
    }

    @Test
    public void updateStock_whenNoExceptionOccured_thenUpdateAndReturnResponse() {
        when(bookRepository.save(any())).then(val -> {
            book.setStock(5);
            return book;
        });
        bookDTO.setBookId(book.getBookId());
        assertEquals(5, bookService.updateBookStock(bookDTO,5).getStock());
    }
}
