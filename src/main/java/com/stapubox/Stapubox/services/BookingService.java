package com.stapubox.Stapubox.services;

import com.stapubox.Stapubox.dtos.BookingDTO;
import com.stapubox.Stapubox.entities.Booking;
import com.stapubox.Stapubox.entities.Slot;
import com.stapubox.Stapubox.enums.BookingStatus;
import com.stapubox.Stapubox.repositories.BookingRepository;
import com.stapubox.Stapubox.repositories.SlotRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {


    private final SlotRepository slotRepo;
    private final BookingRepository bookingRepo;


    @Transactional
    public BookingDTO.BookingResponse bookSlot(BookingDTO.BookingRequest req) {

        Slot slot = slotRepo.findByIdWithLock(req.slotId())
                .orElseThrow(() -> new EntityNotFoundException("Slot not found: " + req.slotId()));


        if (slot.isBooked()) {
            throw new IllegalStateException("Slot already booked.");
        }

        slot.setBooked(true);
        slotRepo.save(slot);

        Booking booking = Booking.builder()
                .slot(slot)
                .userName(req.userName())
                .status(BookingStatus.CONFIRMED)
                .build();

        Booking savedBooking = bookingRepo.save(booking);

        log.info("Booking confirmed: ID {}, User {}, Slot {}", savedBooking.getId(), req.userName(), req.slotId());


        return new BookingDTO.BookingResponse(
                savedBooking.getId(),
                slot.getId(),
                savedBooking.getUserName(),
                savedBooking.getStatus().name(),
                java.time.LocalDateTime.now(),
                slot.getVenue().getName(),
                slot.getVenue().getLocation()
        );
    }


@Transactional
public void cancelBooking(Long id) {
    Booking booking = bookingRepo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Booking not found: " + id));

    if (booking.getStatus() == BookingStatus.CANCELLED) {
        throw new IllegalStateException("Booking is already cancelled.");
    }

    booking.setStatus(BookingStatus.CANCELLED);

    if (booking.getSlot() != null) {
        booking.getSlot().setBooked(false);
        slotRepo.save(booking.getSlot());
    }

    log.info("Booking {} cancelled. Slot {} is now available.", id, booking.getSlot().getId());
}

}
