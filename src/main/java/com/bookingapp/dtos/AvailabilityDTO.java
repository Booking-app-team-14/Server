package com.bookingapp.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AvailabilityDTO {

    @NotNull
    private Long id;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @Min(value = 1)
    private Double specialPrice;
    @NotNull
    private Long accommodation_Id;

    public AvailabilityDTO(Long id, LocalDate start, LocalDate end, Double specialPrice, Long accommodation_Id) {
        this.id = id;
        this.startDate = start;
        this.endDate = end;
        this.specialPrice = specialPrice;
        this.accommodation_Id=accommodation_Id;
    }
}
