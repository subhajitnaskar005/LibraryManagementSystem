package org.jsp.Library_Management.controller;

import org.jsp.Library_Management.dto.Reservation;
import org.jsp.Library_Management.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
	    @Autowired
	    private ReservationService reservationService;

	    @GetMapping
	    public List<Reservation> getActiveReservations() { return reservationService.getActiveReservations(); }

	    @PostMapping
	    public Reservation addReservation(@RequestParam Long bookId, @RequestParam Long memberId) {
	        return reservationService.addReservation(bookId, memberId);
	    }

	    @PutMapping("/cancel/{id}")
	    public Reservation cancelReservation(@PathVariable Long id) {
	        return reservationService.cancelReservation(id);
	    }
	}
