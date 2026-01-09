package com.stapubox.Stapubox.controllers;

import com.stapubox.Stapubox.dtos.SlotDTO;
import com.stapubox.Stapubox.dtos.VenueDTO;
import com.stapubox.Stapubox.entities.Venue;
import com.stapubox.Stapubox.repositories.VenueRepository;
import com.stapubox.Stapubox.services.SlotService;
import com.stapubox.Stapubox.services.VenueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/venues")
@RequiredArgsConstructor
public class VenueController {

    private final VenueRepository venueRepo;
    private   final VenueService venueService;

    private   final SlotService slotService;


    @PostMapping
    public ResponseEntity<Venue> createVenue(@Valid @RequestBody VenueDTO.VenueRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(venueService.createVenue(req));
    }

    @GetMapping
    public ResponseEntity<List<VenueDTO.VenueResponse>> listAllVenues() {
        return ResponseEntity.ok(venueService.getAllVenues());
    }

    @GetMapping("/available")
    public ResponseEntity<List<VenueDTO.VenueResponse>> getAvailableVenues(
            @RequestParam String sport,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {


        return ResponseEntity.ok(venueService.findAvailableVenues(sport, start, end));
    }


    @GetMapping("/{id}")
    public ResponseEntity<VenueDTO.VenueDetailResponse> getDetails(@PathVariable Long id) {
        return ResponseEntity.ok(venueService.getVenueDetails(id));
    }


    @PostMapping("/{venueId}/slots")
    public ResponseEntity<SlotDTO.SlotResponse> addSlot(
            @PathVariable Long venueId,
            @RequestBody SlotDTO.SlotRequest req) {

        // Use slotService here, not venueService
        SlotDTO.SlotResponse response = slotService.addSlotToVenue(venueId, req);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
