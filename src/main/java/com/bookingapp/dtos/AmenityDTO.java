package com.bookingapp.dtos;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmenityDTO {

    private Long id;
    private String name;
    private String description;
    private String icon;

    public AmenityDTO(Long id, String name, String description, String icon) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.icon = icon;
    }


}
