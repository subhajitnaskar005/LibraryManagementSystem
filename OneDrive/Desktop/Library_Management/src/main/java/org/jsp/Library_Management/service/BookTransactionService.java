package org.jsp.Library_Management.service;

import org.jsp.Library_Management.dto.*;
import org.jsp.Library_Management.repository.BookRepository;
import org.jsp.Library_Management.repository.BookTransactionRepository;
import org.jsp.Library_Management.repository.MemberRepository;
import org.jsp.Library_Management.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exception.ResourceNotFoundException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BookTransactionService {

    private static final int LOAN_PERIOD_DAYS = 14;
    private static final double FINE_PER_DAY = 10.0;

    @Autowired private BookTransactionRepository transactionRepository;
    @Autowired private BookRepository bookRepository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private ReservationRepository reservationRepository;

    public List<BookTransaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<BookTransaction> getIssuedTransactions() {
        return transactionRepository.findByStatus("ISSUED");
    }

    public BookTransaction issueBook(Long bookId, Long memberId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + memberId));

        if (book.getAvailableCopies() <= 0) {
            throw new IllegalStateException("No copies available right now. Please join the waitlist instead.");
        }

        BookTransaction transaction = new BookTransaction();
        transaction.setBook(book);
        transaction.setMember(member);
        transaction.setIssueDate(LocalDate.now());
        transaction.setDueDate(LocalDate.now().plusDays(LOAN_PERIOD_DAYS));
        transaction.setStatus("ISSUED");

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        return transactionRepository.save(transaction);
    }

    public BookTransaction returnBook(Long transactionId) {
        BookTransaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + transactionId));

        if ("RETURNED".equals(transaction.getStatus())) {
            throw new IllegalStateException("This book has already been returned");
        }

        LocalDate today = LocalDate.now();
        transaction.setReturnDate(today);
        transaction.setStatus("RETURNED");

        long daysLate = ChronoUnit.DAYS.between(transaction.getDueDate(), today);
        transaction.setFineAmount(daysLate > 0 ? daysLate * FINE_PER_DAY : 0.0);

        Book book = transaction.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);
        transactionRepository.save(transaction);

        List<Reservation> waitlist = reservationRepository
                .findByBookIdAndStatusOrderByReservationDateAsc(book.getId(), "WAITING");

        if (!waitlist.isEmpty()) {
            Reservation nextInLine = waitlist.get(0);

            BookTransaction autoIssue = new BookTransaction();
            autoIssue.setBook(book);
            autoIssue.setMember(nextInLine.getMember());
            autoIssue.setIssueDate(today);
            autoIssue.setDueDate(today.plusDays(LOAN_PERIOD_DAYS));
            autoIssue.setStatus("ISSUED");
            transactionRepository.save(autoIssue);

            book.setAvailableCopies(book.getAvailableCopies() - 1);
            bookRepository.save(book);

            nextInLine.setStatus("FULFILLED");
            reservationRepository.save(nextInLine);
        }

        return transaction;
    }
}