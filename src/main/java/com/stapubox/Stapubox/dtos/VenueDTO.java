package com.stapubox.Stapubox.dtos;

import java.util.List;

public class VenueDTO {

    public record VenueRequest(String name, String location, String sportCode) {}
    public record VenueResponse(Long id, String name, String location, String sportCode) {}


    public record VenueDetailResponse(
            Long id,
            String name,
            String location,
            String sportCode,

            List<SlotDTO.SlotResponse> slots
    ) {}

}
