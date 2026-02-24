package com.example.propertyview.model.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArrivalTime {

    private String checkIn;
    private String checkOut;
}