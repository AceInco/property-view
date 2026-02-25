package com.example.propertyview.mapper;

import com.example.propertyview.model.dto.*;
import com.example.propertyview.model.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HotelMapper {

    @Mapping(target = "address",
            expression = "java(formatAddress(hotel))")
    @Mapping(target = "phone",
            source = "contacts.phone")
    HotelShortDto toShortDto(Hotel hotel);

    HotelFullDto toFullDto(Hotel hotel);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "amenities", ignore = true)
    Hotel toEntity(CreateHotelDto dto);

    default String formatAddress(Hotel hotel) {
        if (hotel.getAddress() == null) return null;
        Address a = hotel.getAddress();
        return a.getHouseNumber() + " " +
                a.getStreet() + ", " +
                a.getCity() + ", " +
                a.getPostCode() + ", " +
                a.getCountry();
    }
}
