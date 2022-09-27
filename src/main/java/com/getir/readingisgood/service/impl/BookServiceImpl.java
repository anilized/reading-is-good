package com.getir.readingisgood.service.impl;

import com.getir.readingisgood.data.dto.BookDTO;
import com.getir.readingisgood.data.entity.Book;
import com.getir.readingisgood.data.mapper.BookMapper;
import com.getir.readingisgood.data.repository.BookRepository;
import com.getir.readingisgood.exception.BookNotFoundException;
import com.getir.readingisgood.exception.NoAvailableStockException;
import com.getir.readingisgood.exception.StockModifiedException;
import com.getir.readingisgood.service.IBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements IBookService {

    private final BookRepository bookRepository;
    private final BookMapper mapper;

    @Override
    public BookDTO findById(Long id) {
        return bookRepository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(BookNotFoundException::new);
    }

    @Override
    public BookDTO createBook(BookDTO bookDTO) {
        Book book = mapper.toEntity(bookDTO);
        bookRepository.save(book);
        return mapper.toDTO(book);
    }

    @Override
    public BookDTO updateBookStock(BookDTO bookDTO, int stock) throws StockModifiedException {
            Book book = mapper.toEntity(bookDTO);
            book.setStock(stock);
            try {
                bookRepository.save(book);
                return mapper.toDTO(book);
            } catch (OptimisticLockingFailureException e) {
                throw new StockModifiedException();
            }
    }

    @Override
    public boolean isStockAvailable(BookDTO bookDTO, int amount) {
        if(bookDTO.getStock() < amount) {
            throw new NoAvailableStockException();
        } else {
            return true;
        }
    }
}
