package com.stapubox.Stapubox.controllers;
import com.stapubox.Stapubox.dtos.BookingDTO;
import com.stapubox.Stapubox.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;



    @PostMapping
    public ResponseEntity<BookingDTO.BookingResponse> createBooking(@RequestBody BookingDTO.BookingRequest req) {

        return ResponseEntity.ok(bookingService.bookSlot(req));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
        return ResponseEntity.noContent().build();
    }



}
