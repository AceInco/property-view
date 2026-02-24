package com.example.propertyview.repository;

import com.example.propertyview.model.entity.Hotel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long>,
        JpaSpecificationExecutor<Hotel> {

    @Query("SELECT h.brand, COUNT(h) FROM Hotel h GROUP BY h.brand")
    List<Object[]> countByBrand();

    @Query("SELECT h.address.city, COUNT(h) FROM Hotel h GROUP BY h.address.city")
    List<Object[]> countByCity();

    @Query("SELECT h.address.country, COUNT(h) FROM Hotel h GROUP BY h.address.country")
    List<Object[]> countByCountry();

    @Query("SELECT a, COUNT(a) FROM Hotel h JOIN h.amenities a GROUP BY a")
    List<Object[]> countByAmenity();
}
