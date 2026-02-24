package com.example.propertyview.model.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contacts {

    private String phone;
    private String email;
}