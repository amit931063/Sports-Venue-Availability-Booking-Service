package com.stapubox.Stapubox.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class BookingDTO {
    public record BookingRequest(
            @NotNull(message = "Slot ID is required")
            Long slotId,
            @NotBlank(message = "User name cannot be empty")
            String userName) {}

    public record BookingResponse(
            Long bookingId,
            Long slotId,
            String userName,
            String status, // e.g., "CONFIRMED"
            LocalDateTime bookingTime,
            String venueName,
            String location
    ) {}

}
