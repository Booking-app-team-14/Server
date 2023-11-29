package com.bookingapp.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter

public class GuestReservationDTO {



    private Long reservationId;

    private Long accommodationId;

    private LocalDate startDate;

    private LocalDate endDate;

    public GuestReservationDTO(Long reservationId, Long accommodationId, LocalDate startDate, LocalDate endDate){
        this.reservationId = reservationId;
        this.accommodationId = accommodationId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

}