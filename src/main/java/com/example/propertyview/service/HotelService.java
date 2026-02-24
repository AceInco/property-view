package com.example.propertyview.service;

import com.example.propertyview.model.dto.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface HotelService {

    List<HotelShortDto> getAll();

    HotelFullDto getById(Long id);

    HotelShortDto create(CreateHotelDto dto);

    void addAmenities(Long id, Set<String> amenities);

    List<HotelShortDto> search(
            String name,
            String brand,
            String city,
            String country,
            String amenity
    );

    Map<String, Long> histogram(String param);
}
