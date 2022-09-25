package com.getir.readingisgood.service.impl;

import com.getir.readingisgood.data.dto.BookDTO;
import com.getir.readingisgood.data.entity.Book;
import com.getir.readingisgood.data.mapper.BookMapper;
import com.getir.readingisgood.data.repository.BookRepository;
import com.getir.readingisgood.service.IBookService;
import lombok.RequiredArgsConstructor;
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
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public BookDTO createBook(BookDTO bookDTO) {
        Book book = mapper.toEntity(bookDTO);
        bookRepository.save(book);
        return mapper.toDTO(book);
    }

    @Override
    public BookDTO updateBookStock(BookDTO bookDTO, int stock) throws RuntimeException{
        Book book = mapper.toEntity(bookDTO);
        book.setStock(stock);
        try {
            bookRepository.save(book);
            return mapper.toDTO(book);
        } catch (RuntimeException e) {
            throw e;
        }
    }

    @Override
    public boolean isStockAvailable(BookDTO bookDTO, int amount) {
        return bookDTO.getStock() > amount;
    }
}
