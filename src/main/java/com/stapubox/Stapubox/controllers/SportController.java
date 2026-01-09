package com.stapubox.Stapubox.controllers;


import com.stapubox.Stapubox.dtos.BookingDTO;
import com.stapubox.Stapubox.dtos.VenueDTO;
import com.stapubox.Stapubox.entities.Booking;
import com.stapubox.Stapubox.entities.Sport;
import com.stapubox.Stapubox.entities.Venue;
import com.stapubox.Stapubox.services.BookingService;
import com.stapubox.Stapubox.services.SportsService;
import com.stapubox.Stapubox.services.VenueService;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/sports")
@RequiredArgsConstructor
public class SportController {
private final VenueService venueService;
    private final SportsService sportsService;
    @PostMapping
    public ResponseEntity<Sport> addSport(@RequestBody Sport sport) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sportsService.addSport(sport));
    }



}
