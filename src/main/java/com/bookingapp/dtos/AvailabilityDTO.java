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

    public AvailabilityDTO(Long id, LocalDate start, LocalDate end, Double specialPrice) {
        this.id = id;
        this.startDate = start;
        this.endDate = end;
        this.specialPrice = specialPrice;
    }
}
