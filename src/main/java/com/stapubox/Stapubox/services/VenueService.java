package com.stapubox.Stapubox.services;

import com.stapubox.Stapubox.dtos.SlotDTO;
import com.stapubox.Stapubox.dtos.VenueDTO;
import com.stapubox.Stapubox.entities.Venue;
import com.stapubox.Stapubox.repositories.VenueRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class VenueService {


    private final VenueRepository venueRepository;
    private final SportsService sportService; // Service that calls the external API


    @Transactional
    public Venue createVenue(VenueDTO.VenueRequest req) {

        if (!sportService.isValidSport(req.sportCode())) {
            throw new IllegalArgumentException("Invalid sport code: " + req.sportCode());
        }

        return venueRepository.save(Venue.builder()
                .name(req.name())
                .location(req.location())
                .sportCode(req.sportCode())
                .build());
    }

    @Transactional(readOnly = true)
    public List<VenueDTO.VenueResponse> getAllVenues() {
        return venueRepository.findAll().stream()
                .map(v -> new VenueDTO.VenueResponse(v.getId(), v.getName(), v.getLocation(), v.getSportCode()))
                .toList();
    }

@Transactional(readOnly = true)
public List<VenueDTO.VenueResponse> findAvailableVenues(String sport, LocalDateTime start, LocalDateTime end) {
    log.info("Searching for available {} venues between {} and {}", sport, start, end);

    if (start.isAfter(end)) {
        throw new IllegalArgumentException("Start time must be before end time");
    }


    List<Venue> venues = venueRepository.findAvailableVenues(sport, start, end);


    return venues.stream()
            .map(v -> new VenueDTO.VenueResponse(
                    v.getId(),
                    v.getName(),
                    v.getLocation(),
                    v.getSportCode()))
            .toList();
}

    @Transactional(readOnly = true)
    public VenueDTO.VenueDetailResponse getVenueDetails(Long id) {

        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Venue not found with id: " + id));


        List<SlotDTO.SlotResponse> slotDtos = venue.getSlots().stream()
                .map(slot -> new SlotDTO.SlotResponse(
                        slot.getId(),
                        slot.getStartTime(),
                        slot.getEndTime(),
                        slot.isBooked()))
                .toList();


        return new VenueDTO.VenueDetailResponse(
                venue.getId(),
                venue.getName(),
                venue.getLocation(),
                venue.getSportCode(),
                slotDtos
        );
    }

}
