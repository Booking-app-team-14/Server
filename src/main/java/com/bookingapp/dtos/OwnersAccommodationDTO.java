package com.bookingapp.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OwnersAccommodationDTO {
    private Long accommodationId;
    private String name;
    private String description;

    public OwnersAccommodationDTO(Long accommodationId, String name, String description){
        this.accommodationId = accommodationId;
        this.name = name;
        this.description = description;
    }
}