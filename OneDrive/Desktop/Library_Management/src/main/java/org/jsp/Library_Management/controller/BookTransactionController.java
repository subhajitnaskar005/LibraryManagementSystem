package org.jsp.Library_Management.controller;


import org.jsp.Library_Management.dto.BookTransaction;
import org.jsp.Library_Management.service.BookTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/transactions")
public class BookTransactionController {
	    @Autowired
	    private BookTransactionService transactionService;

	    @GetMapping
	    public List<BookTransaction> getAllTransactions() {
	    	return transactionService.getAllTransactions(); 
	    	}

	    @GetMapping("/issued")
	    public List<BookTransaction> getIssuedTransactions() { 
	    	return transactionService.getIssuedTransactions(); 
	    	}

	    @PostMapping("/issue")
	    public BookTransaction issueBook(@RequestParam Long bookId, @RequestParam Long memberId) {
	        return transactionService.issueBook(bookId, memberId);
	    }

	    @PutMapping("/return/{transactionId}")
	    public BookTransaction returnBook(@PathVariable Long transactionId) {
	        return transactionService.returnBook(transactionId);
	    }
	    
//	    @DeleteMapping({"/memberId"})
//	    public BookTransaction removeDetails(@PathVariable Long memberId) {
//	    	return transactionService.removeDetails(memberId);
//	    }
	}
