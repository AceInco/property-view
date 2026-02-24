package com.example.propertyview.model.dto;

import lombok.Builder;

@Builder
public record HotelShortDto(
        Long id,
        String name,
        String description,
        String address,
        String phone
) {
}
