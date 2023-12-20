package com.bookingapp.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AvailabilityDTO {

    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double specialPrice;
    private Long accommodation_Id;

    public AvailabilityDTO(Long id, LocalDate start, LocalDate end, Double specialPrice, Long accommodation_Id) {
        this.id = id;
        this.startDate = start;
        this.endDate = end;
        this.specialPrice = specialPrice;
        this.accommodation_Id=accommodation_Id;
    }
}
