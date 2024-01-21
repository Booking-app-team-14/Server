package com.bookingapp.entities;

import com.bookingapp.dtos.AmenityDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Amenity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 5, max = 50)
    @Column(nullable=false)
    private String name;

    @NotEmpty
    @Column(nullable=false)
    private String description;

    @NotEmpty
    @Column(nullable=false)
    private String icon;

    public Amenity(AmenityDTO amenityDTO) {
        //this.id=amenityDTO.getId();
        this.name=amenityDTO.getName();
        this.description=amenityDTO.getDescription();
        this.icon=amenityDTO.getIcon();
    }

    public Amenity() { }

}
