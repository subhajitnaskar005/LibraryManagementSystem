package org.jsp.Library_Management.service;

import org.jsp.Library_Management.dto.*;
import org.jsp.Library_Management.repository.BookRepository;
import org.jsp.Library_Management.repository.MemberRepository;
import org.jsp.Library_Management.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exception.ResourceNotFoundException;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {

    @Autowired private ReservationRepository reservationRepository;
    @Autowired private BookRepository bookRepository;
    @Autowired private MemberRepository memberRepository;

    public Reservation addReservation(Long bookId, Long memberId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + memberId));

        if (book.getAvailableCopies() > 0) {
            throw new IllegalStateException("This book is currently available — please issue it directly instead of reserving.");
        }

        Reservation reservation = new Reservation();
        reservation.setBook(book);
        reservation.setMember(member);
        reservation.setReservationDate(LocalDate.now());
        reservation.setStatus("WAITING");

        return reservationRepository.save(reservation);
    }

    public List<Reservation> getActiveReservations() {
        return reservationRepository.findByStatus("WAITING");
    }

    public Reservation cancelReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: " + id));
        reservation.setStatus("CANCELLED");
        return reservationRepository.save(reservation);
    }
}