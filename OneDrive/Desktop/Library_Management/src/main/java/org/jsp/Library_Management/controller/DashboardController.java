package org.jsp.Library_Management.controller;

import org.jsp.Library_Management.dto.DashboardStatsDTO;
import org.jsp.Library_Management.dto.*;
import org.jsp.Library_Management.repository.BookRepository;
import org.jsp.Library_Management.repository.BookTransactionRepository;
import org.jsp.Library_Management.repository.MemberRepository;
import org.jsp.Library_Management.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired private BookRepository bookRepository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private BookTransactionRepository transactionRepository;
    @Autowired private ReservationRepository reservationRepository;

    @GetMapping("/summary")
    public DashboardStatsDTO getSummary() {
        long totalBooks = bookRepository.count();
        long totalMembers = memberRepository.count();

        List<BookTransaction> issued = transactionRepository.findByStatus("ISSUED");
        long totalIssued = issued.size();
        long overdue = issued.stream().filter(t -> t.getDueDate().isBefore(LocalDate.now())).count();

        double totalFine = transactionRepository.findByStatus("RETURNED").stream()
                .mapToDouble(t -> t.getFineAmount() == null ? 0 : t.getFineAmount())
                .sum();

        long totalWaitlisted = reservationRepository.findByStatus("WAITING").size();

        return new DashboardStatsDTO(totalBooks, totalMembers, totalIssued, overdue, totalWaitlisted, totalFine);
    }
}