package org.jsp.Library_Management.controller;
import org.jsp.Library_Management.dto.Book;
import org.jsp.Library_Management.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "http://localhost:5173")
public class BookController {
	    @Autowired
	    private BookService bookService;

	    @GetMapping
	    public List<Book> getAllBooks() {
	    	return bookService.getAllBooks(); 
	    }

	    @GetMapping("/{id}")
	    public Book getBookById(@PathVariable Long id) { 
	    	return bookService.getBookById(id); 
	    	}

	    @GetMapping("/search")
	    public List<Book> searchBooks(@RequestParam String keyword) { return bookService.searchBooks(keyword); }

	    @PostMapping
	    public Book addBook(@RequestBody Book book) 
	    { 
	    	return bookService.addBook(book); 
	    }

	    @PutMapping("/{id}")
	    public Book updateBook(@PathVariable Long id, @RequestBody Book book) { 
	    	return bookService.updateBook(id, book); 
	    	}

	    @DeleteMapping("/{id}")
	    public void deleteBook(@PathVariable Long id) {
	    	bookService.deleteBook(id); 
	    	}
	}
