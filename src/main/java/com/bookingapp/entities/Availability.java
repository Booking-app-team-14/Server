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

    @ManyToOne
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    public Availability(AvailabilityDTO availabilityDTO) {
        //this.id=availabilityDTO.getId();
        this.startDate=availabilityDTO.getStartDate();
        this.endDate=availabilityDTO.getEndDate();
        this.specialPrice=availabilityDTO.getSpecialPrice();
    }

    public Availability() {
        this.startDate=LocalDate.now();
        this.endDate=LocalDate.now();
        this.specialPrice=0.0;
        this.accommodation=null;
    }
}
