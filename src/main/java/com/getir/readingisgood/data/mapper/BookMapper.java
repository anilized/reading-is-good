package com.getir.readingisgood.data.mapper;

import com.getir.readingisgood.data.dto.BookDTO;
import com.getir.readingisgood.data.entity.Book;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class BookMapper implements Mapper<Book, BookDTO>{

    @Override
    public BookDTO toDTO(Book book) {
        return BookDTO.builder()
                .bookId(book.getBookId())
                .name(book.getName())
                .price(book.getPrice())
                .stock(book.getStock())
                .authorName(book.getAuthorName())
                .version(book.getVersion())
                .build();
    }

    @Override
    public Book toEntity(BookDTO bookDTO) {
        return Book.builder()
                .bookId(bookDTO.getBookId())
                .name(bookDTO.getName())
                .authorName(bookDTO.getAuthorName())
                .stock(bookDTO.getStock())
                .price(bookDTO.getPrice())
                .version(bookDTO.getVersion())
                .build();
    }
}
