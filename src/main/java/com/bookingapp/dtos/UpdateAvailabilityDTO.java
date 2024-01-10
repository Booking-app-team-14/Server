package com.bookingapp.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UpdateAvailabilityDTO {

    private LocalDate startDate;
    private LocalDate endDate;
    private Double specialPrice;

    public UpdateAvailabilityDTO(LocalDate startDate, LocalDate endDate, Double specialPrice) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.specialPrice = specialPrice;
    }

    public UpdateAvailabilityDTO() {
    }

}
