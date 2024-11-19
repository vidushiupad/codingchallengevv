package com.hexaware.Book.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.Book.Dao.BookRepository;
import com.hexaware.Book.Entity.Book;


@Service
public class BookService {
    @Autowired
    BookRepository repoBook;

    public Book addBook(Book book) {
        return repoBook.save(book);
    }

    public List<Book> getBooks() {
        return (List<Book>) repoBook.findAll();
    }

    public Book delete(long isbn) {
        Book bk = repoBook.findById(isbn).orElse(null);
        if (bk != null) {
            repoBook.delete(bk);
        }
        return bk;
    }

    public String updatebook(long isbn, String title) {
        Book bk = repoBook.findById(isbn).orElse(null);
        if (bk == null) {
            return "Not Found";
        } else {
            bk.setTitle(title);
            repoBook.save(bk);
            return "Name Updated";
        }
    }

    public Book findbyIsbn(long isbn) {
        return repoBook.findById(isbn).orElse(null);
    }
}
