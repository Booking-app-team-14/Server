package com.bookingapp.entities;

import com.bookingapp.dtos.AmenityDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Amenity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private String description;

    @Column(nullable=false)
    private String icon;

    public Amenity(AmenityDTO amenityDTO) {
        //this.id=amenityDTO.getId();
        this.name=amenityDTO.getName();
        this.description=amenityDTO.getDescription();
        this.icon=amenityDTO.getIcon();
    }

    public Amenity() {
        //this.id=amenityDTO.getId();
        this.name=this.getName();
        this.description=this.getDescription();
        this.icon=this.getIcon();
    }
}
