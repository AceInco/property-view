package com.example.propertyview.controller;

import com.example.propertyview.model.dto.*;
import com.example.propertyview.service.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/property-view")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService service;

    @GetMapping("/hotels")
    public List<HotelShortDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/hotels/{id}")
    public HotelFullDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/hotels")
    @ResponseStatus(HttpStatus.CREATED)
    public HotelShortDto create(@Valid @RequestBody CreateHotelDto dto) {
        return service.create(dto);
    }

    @PostMapping("/hotels/{id}/amenities")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addAmenities(@PathVariable Long id,
                             @RequestBody Set<String> amenities) {
        service.addAmenities(id, amenities);
    }

    @GetMapping("/search")
    public List<HotelShortDto> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String amenities
    ) {
        return service.search(name, brand, city, country, amenities);
    }

    @GetMapping("/histogram/{param}")
    public Map<String, Long> histogram(@PathVariable String param) {
        return service.histogram(param);
    }
}
