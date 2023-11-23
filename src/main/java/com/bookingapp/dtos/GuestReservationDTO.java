package com.bookingapp.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter

public class GuestReservationDTO {


    private Long id;

    private Long reservationId;

    private Long accommodationId;

    private LocalDate startDate;

    private LocalDate endDate;

    public GuestReservationDTO(Long id, Long reservationId, Long accommodationId, LocalDate startDate, LocalDate endDate){
        this.id = id;
        this.reservationId = reservationId;
        this.accommodationId = accommodationId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

}