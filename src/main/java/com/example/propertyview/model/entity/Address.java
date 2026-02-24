package com.example.propertyview.model.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    private Integer houseNumber;
    private String street;
    private String city;
    private String country;
    private String postCode;
}