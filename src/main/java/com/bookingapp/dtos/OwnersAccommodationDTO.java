package com.bookingapp.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OwnersAccommodationDTO {
    private Long ownerId;
    private Long accommodationId;
    private String name;
    private String description;

    public OwnersAccommodationDTO(Long ownerId,Long accommodationId, String name, String description){
        this.ownerId=ownerId;
        this.accommodationId = accommodationId;
        this.name = name;
        this.description = description;
    }
}