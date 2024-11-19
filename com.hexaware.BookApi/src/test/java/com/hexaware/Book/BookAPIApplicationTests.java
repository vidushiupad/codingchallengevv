package com.hexaware.Book;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.Book.Dao.BookRepository;
import com.hexaware.Book.Entity.Book;

@SpringBootTest
class BookAPIApplicationTests {
	@Autowired BookRepository bookRep;

	@Test
	void addNewbook() {
		Book b= new Book();
		b.setTitle("Puthumai pen");
		b.setIsbn(106);
		b.setAuthor("P. Subramaniyam");
		b.setPublicationYear(1979);
		bookRep.save(b);
	}
	
	@Test
	void deleteExistingBook() {
		bookRep.deleteById((long) 106);
	}
	

	@Test
    void getAllBooksInTheTable() {
        List<Book> actualBookList = (List<Book>) bookRep.findAll();
        int listSize = actualBookList.size();
        
        assertEquals(6, listSize);
    }
	
	@Test
	void getBookByIdInTheTable() {
		Book actualBook = bookRep.findById((long) 101).orElse(null);
		
		
		Book expBook = new Book();
		expBook.setTitle("Ponniyin Selvan 1");
		
		assertEquals(actualBook.getTitle(), expBook.getTitle(), "");
	}
	
	@Test
	void updateBookInTheTable() {
		Book actualBook = bookRep.findById((long) 102).orElse(null);
		
		actualBook.setTitle("java");
		
		bookRep.save(actualBook);
		
		assertEquals(actualBook.getTitle(), "java", "");
	}

}