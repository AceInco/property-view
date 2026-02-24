package com.example.propertyview.service.impl;

import com.example.propertyview.exception.BadRequestException;
import com.example.propertyview.mapper.HotelMapper;
import com.example.propertyview.model.dto.*;
import com.example.propertyview.model.entity.Hotel;
import com.example.propertyview.repository.HotelRepository;
import com.example.propertyview.service.HotelService;
import com.example.propertyview.specification.HotelSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class HotelServiceImpl implements HotelService {

    private final HotelRepository repository;
    private final HotelMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<HotelShortDto> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toShortDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public HotelFullDto getById(Long id) {
        Hotel hotel = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return mapper.toFullDto(hotel);
    }

    @Override
    public HotelShortDto create(CreateHotelDto dto) {
        Hotel hotel = mapper.toEntity(dto);
        Hotel saved = repository.save(hotel);
        return mapper.toShortDto(saved);
    }

    @Override
    public void addAmenities(Long id, Set<String> amenities) {
        Hotel hotel = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        hotel.getAmenities().addAll(amenities);
        repository.save(hotel);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HotelShortDto> search(
            String name,
            String brand,
            String city,
            String country,
            String amenity) {

        Specification<Hotel> spec =
                HotelSpecification.build(name, brand, city, country, amenity);

        return repository.findAll(spec)
                .stream()
                .map(mapper::toShortDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Long> histogram(String param) {

        List<Object[]> result = switch (param) {
            case "brand" -> repository.countByBrand();
            case "city" -> repository.countByCity();
            case "country" -> repository.countByCountry();
            case "amenities" -> repository.countByAmenity();
            default -> throw new BadRequestException("Unsupported param");
        };

        return result.stream()
                .collect(Collectors.toMap(
                        r -> (String) r[0],
                        r -> (Long) r[1]
                ));
    }
}
