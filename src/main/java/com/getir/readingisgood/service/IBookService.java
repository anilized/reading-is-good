package com.getir.readingisgood.service;

import com.getir.readingisgood.data.dto.BookDTO;

public interface IBookService {

    BookDTO findById(Long id);
    BookDTO createBook(BookDTO bookDTO);
    BookDTO updateBookStock(BookDTO bookDTO, int newStock);
    boolean isStockAvailable(BookDTO bookDTO, int amount);
}
