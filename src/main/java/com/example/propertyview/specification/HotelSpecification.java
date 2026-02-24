package com.example.propertyview.specification;

import com.example.propertyview.model.entity.Hotel;
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
                predicates.add(cb.equal(root.get("brand"), brand));

            if (city != null)
                predicates.add(cb.equal(root.get("address").get("city"), city));

            if (country != null)
                predicates.add(cb.equal(root.get("address").get("country"), country));

            if (amenity != null)
                predicates.add(cb.isMember(amenity, root.get("amenities")));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
