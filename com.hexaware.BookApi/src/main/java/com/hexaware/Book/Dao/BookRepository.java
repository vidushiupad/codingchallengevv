package com.hexaware.Book.Dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.Book.Entity.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
	Book findByIsbn(long isbn);
	
	
}
