package com.example.propertyview.specification;

import com.example.propertyview.model.entity.Hotel;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class HotelSpecification {

    public static Specification<Hotel> build(
            String name,
            String brand,
            String city,
            String country,
            String amenity
    ) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (name != null)
                predicates.add(cb.like(cb.lower(root.get("name")),
                        "%" + name.toLowerCase() + "%"));

            if (brand != null)
                predicates.add(cb.equal(
                        cb.lower(root.get("brand")),
                        brand.toLowerCase()
                ));

            if (city != null)
                predicates.add(cb.equal(
                        cb.lower(root.get("address").get("city")),
                        city.toLowerCase()
                ));

            if (country != null)
                predicates.add(cb.equal(
                        cb.lower(root.get("address").get("country")),
                        country.toLowerCase()
                ));

            if (amenity != null) {
                Join<Hotel, String> join = root.join("amenities");
                predicates.add(cb.equal(
                        cb.lower(join),
                        amenity.toLowerCase()
                ));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
