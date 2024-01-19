package com.bookingapp.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter

public class GuestReservationDTO {

    @NotNull
    private Long reservationId;
    @NotNull
    private Long accommodationId;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;

    public GuestReservationDTO(Long reservationId, Long accommodationId, LocalDate startDate, LocalDate endDate){
        this.reservationId = reservationId;
        this.accommodationId = accommodationId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

}