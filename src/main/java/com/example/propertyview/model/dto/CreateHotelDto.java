package com.example.propertyview.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record CreateHotelDto(

        @NotBlank
        String name,

        String description,

        @NotBlank
        String brand,

        @Valid
        AddressDto address,

        @Valid
        ContactsDto contacts,

        @Valid
        ArrivalTimeDto arrivalTime
) {
}
