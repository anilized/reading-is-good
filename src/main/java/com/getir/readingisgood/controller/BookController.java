package com.getir.readingisgood.controller;

import com.getir.readingisgood.controller.base.IBaseController;
import com.getir.readingisgood.data.dto.BookDTO;
import com.getir.readingisgood.service.IBookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@Tag(name = "book", description = "Book API")
@RequestMapping("/api/book")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
public class BookController implements IBaseController {

    private final IBookService bookService;

    @PostMapping
    @Operation(summary = "Create book", description = "Create book", tags = "book")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = BookDTO.class))), @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = BookDTO.class)))})
    public ResponseEntity<BookDTO> createBook(@RequestBody @Valid BookDTO bookDTO) {
        return new ResponseEntity<>(bookService.createBook(bookDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update book stock", description = "Update book stock", tags = "book")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = BookDTO.class))), @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = BookDTO.class))), @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = BookDTO.class))),})
    public ResponseEntity<BookDTO> updateBookStock(@PathVariable @Parameter(name = "id", description = "book id") Long id, @Parameter(name = "stock") @Min(0) int stock) {
        BookDTO bookDTO = bookService.findById(id);
        return ResponseEntity.ok(bookService.updateBookStock(bookDTO, stock));
    }

}
