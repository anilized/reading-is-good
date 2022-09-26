package com.getir.readingisgood.controller;

import com.getir.readingisgood.controller.base.IBaseController;
import com.getir.readingisgood.data.dto.BookDTO;
import com.getir.readingisgood.service.IBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/api/book")
@PreAuthorize("hasRole('ROLE_CUSTOMER')")
@RequiredArgsConstructor
public class BookController implements IBaseController {

    private final IBookService bookService;

    @PostMapping("/create")
    public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO bookDTO) {
        return new ResponseEntity<>(bookService.createBook(bookDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBookStock(@PathVariable Long id, @RequestParam @Min(0) int stock) {
        BookDTO bookDTO = bookService.findById(id);
        return ResponseEntity.ok(bookService.updateBookStock(bookDTO, stock));
    }

}
