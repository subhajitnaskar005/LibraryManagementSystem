package org.jsp.Library_Management.repository;

import org.jsp.Library_Management.dto.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByBookIdAndStatusOrderByReservationDateAsc(Long bookId, String status);
    List<Reservation> findByStatus(String status);
}