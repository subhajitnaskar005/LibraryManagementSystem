package org.jsp.Library_Management.service;
//import com.library.management.exception.ResourceNotFoundException;
//import com.library.management.repository.BookRepository;

import org.jsp.Library_Management.dto.Book;
import org.jsp.Library_Management.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exception.ResourceNotFoundException;

import java.util.List;

@Service
public class BookService {
	    @Autowired
	    private BookRepository bookRepository;

	    public List<Book> getAllBooks() {
	        return bookRepository.findAll();
	    }

	    public Book getBookById(Long id) {
	        return bookRepository.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
	    }

	    public Book addBook(Book book) {
	        book.setAvailableCopies(book.getTotalCopies());
	        return bookRepository.save(book);
	    }

	    public Book updateBook(Long id, Book bookDetails) {
	        Book book = getBookById(id);
	        int issuedCopies = book.getTotalCopies() - book.getAvailableCopies();
	        book.setTitle(bookDetails.getTitle());
	        book.setAuthor(bookDetails.getAuthor());
	        book.setIsbn(bookDetails.getIsbn());
	        book.setCategory(bookDetails.getCategory());
	        book.setTotalCopies(bookDetails.getTotalCopies());
	        book.setAvailableCopies(bookDetails.getTotalCopies() - issuedCopies);
	        return bookRepository.save(book);
	    }

	    public void deleteBook(Long id) {
	        bookRepository.delete(getBookById(id));
	    }

	    public List<Book> searchBooks(String keyword) {
	        return bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(keyword, keyword);
	    }
}
