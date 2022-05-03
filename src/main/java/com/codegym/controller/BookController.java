package com.codegym.controller;

import com.codegym.model.Book;
import com.codegym.service.book.IBookService;
import com.codegym.service.category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private IBookService bookService;
    @Autowired
    private ICategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<Iterable<Book>> listAllBooks() {
        Iterable<Book> books = bookService.findAll();
        List<Book> bookList = (List<Book>) books;
        if (bookList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        bookService.save(book);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        Optional<Book> bookOptional = bookService.findById(id);
        if (bookOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        book.setId(bookOptional.get().getId());
        bookService.save(book);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable Long id, @RequestBody Book book) {
        Optional<Book> bookOptional = bookService.findById(id);
        if (bookOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Book book1 = bookOptional.get();
        bookService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
