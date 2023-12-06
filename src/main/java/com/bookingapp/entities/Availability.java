package com.bookingapp.entities;

import com.bookingapp.dtos.AvailabilityDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private LocalDate startDate;

    @Column(nullable=false)
    private LocalDate endDate;

    @Column(nullable=false)
    private Double specialPrice;

    public Availability(AvailabilityDTO availabilityDTO) {
        //this.id=availabilityDTO.getId();
        this.startDate=availabilityDTO.getStartDate();
        this.endDate=availabilityDTO.getEndDate();
        this.specialPrice=availabilityDTO.getSpecialPrice();
    }

    public Availability() {
        //this.id=availabilityDTO.getId();
        this.startDate=this.getStartDate();
        this.endDate=this.getEndDate();
        this.specialPrice=this.getSpecialPrice();
    }
}
