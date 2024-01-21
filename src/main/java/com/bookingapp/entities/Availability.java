package com.bookingapp.entities;

import com.bookingapp.dtos.AvailabilityDTO;
import com.bookingapp.dtos.UpdateAvailabilityDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @Column(nullable=false)
    private LocalDate startDate;

    @NotNull
    @Column(nullable=false)
    private LocalDate endDate;

    @Column(nullable=true)
    private Double specialPrice;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    public Availability(AvailabilityDTO availabilityDTO) {
        //this.id=availabilityDTO.getId();
        this.startDate=availabilityDTO.getStartDate();
        this.endDate=availabilityDTO.getEndDate();
        this.specialPrice=availabilityDTO.getSpecialPrice();
    }

    public Availability(UpdateAvailabilityDTO availabilityDTO) {
        this.startDate=availabilityDTO.getStartDate();
        this.endDate=availabilityDTO.getEndDate();
        this.specialPrice=availabilityDTO.getSpecialPrice();
        this.accommodation=null;
    }

    public Availability() {
        this.startDate=LocalDate.now();
        this.endDate=LocalDate.now();
        this.specialPrice=0.0;
        this.accommodation=null;
    }

    public Availability(LocalDate startDate, LocalDate endDate, Double specialPrice, Accommodation accommodation) {
        this.startDate=startDate;
        this.endDate=endDate;
        this.specialPrice=specialPrice;
        this.accommodation=accommodation;
    }

}
