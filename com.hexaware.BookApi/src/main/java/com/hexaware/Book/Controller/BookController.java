package com.hexaware.Book.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.Book.Entity.Book;
import com.hexaware.Book.Service.BookService;



@RestController
@RequestMapping("/api/admin")
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("/addNewBook")
    public Book addNewBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @GetMapping("/getAllBooks")
    public List<Book> getAllBooks() {
        return bookService.getBooks();
    }

    @DeleteMapping("/deleteBook/{isbn}")
    public Book deleteBook(@PathVariable("isbn") long isbn) {
        return bookService.delete(isbn);
    }

    @PutMapping("/updateBook/{isbn}/{title}")
    public String updateBook(@PathVariable("isbn") long isbn, @PathVariable("title") String title) {
        return bookService.updatebook(isbn,title);
    }

    @GetMapping("/findByISBN/{isbn}")
    public ResponseEntity<Book> findByIsbn(@PathVariable("isbn") long isbn) {
        Book book = bookService.findbyIsbn(isbn);
        if (book != null) {
            return new ResponseEntity<>(book, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}