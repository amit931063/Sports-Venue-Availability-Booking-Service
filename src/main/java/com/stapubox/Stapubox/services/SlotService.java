package com.stapubox.Stapubox.services;


import com.stapubox.Stapubox.dtos.SlotDTO;
import com.stapubox.Stapubox.entities.Slot;
import com.stapubox.Stapubox.entities.Venue;
import com.stapubox.Stapubox.repositories.SlotRepository;
import com.stapubox.Stapubox.repositories.VenueRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SlotService {

    private final SlotRepository slotRepository;
    private final VenueRepository venueRepository;



    @Transactional
    public SlotDTO.SlotResponse addSlotToVenue(Long venueId, SlotDTO.SlotRequest req) {

        Venue venue = venueRepository.findById(venueId)
                .orElseThrow(() -> new EntityNotFoundException("Venue not found: " + venueId));


        List<Slot> overlaps = slotRepository.findOverlappingSlots(
                venueId, req.startTime(), req.endTime());

        if (!overlaps.isEmpty()) {

            throw new IllegalStateException("Slot overlaps with an existing time slot.");
        }

        Slot slot = Slot.builder()
                .venue(venue)
                .startTime(req.startTime())
                .endTime(req.endTime())
                .isBooked(false)
                .build();


        Slot savedSlot = slotRepository.save(slot);


        return new SlotDTO.SlotResponse(
                savedSlot.getId(),
                savedSlot.getStartTime(),
                savedSlot.getEndTime(),
                savedSlot.isBooked()
        );
    }

}
