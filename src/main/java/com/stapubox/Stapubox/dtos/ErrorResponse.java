package com.stapubox.Stapubox.dtos;

import java.time.LocalDateTime;

    public record ErrorResponse(int status, String message, LocalDateTime timestamp) {

    }
