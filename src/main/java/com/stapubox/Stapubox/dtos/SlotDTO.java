package com.stapubox.Stapubox.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class SlotDTO {
    public record SlotRequest(
            @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
            LocalDateTime startTime,
            @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
            LocalDateTime endTime) {}
    public record SlotResponse(
            Long id,
            LocalDateTime startTime,
            LocalDateTime endTime,
            boolean isBooked
    ) {}
}
