package com.polarbookshop.catalogservice.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.polarbookshop.catalogservice.domain.Book;
import com.polarbookshop.catalogservice.domain.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping // (3)
    public Iterable<Book> get() {
        return bookService.viewBookList();
    }

    @GetMapping("{isbn}") // (4)
    public Book getByIsbn(@PathVariable String isbn) { // (5)
        return bookService.viewBookDetails(isbn);
    }

    @PostMapping // (6)
    @ResponseStatus(HttpStatus.CREATED) // (7)
    public Book post(@Valid @RequestBody Book book) { // (8)
        return bookService.addBookToCatalog(book);
    }

    @DeleteMapping("{isbn}") // (9)
    @ResponseStatus(HttpStatus.NO_CONTENT) // (10)
    public void delete(@PathVariable String isbn) {
        bookService.removeBookFromCatalog(isbn);
    }

    @PutMapping("{isbn}") // (11)
    public Book put(@PathVariable String isbn, @Valid @RequestBody Book book) {
        return bookService.editBookDetails(isbn, book);
    }

}
