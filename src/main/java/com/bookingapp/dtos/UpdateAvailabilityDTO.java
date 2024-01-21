package com.bookingapp.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class UpdateAvailabilityDTO implements Serializable {

    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @Min(value = 1)
    private Double specialPrice;

    public UpdateAvailabilityDTO(LocalDate startDate, LocalDate endDate, Double specialPrice) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.specialPrice = specialPrice;
    }

    public UpdateAvailabilityDTO() {
    }

}
