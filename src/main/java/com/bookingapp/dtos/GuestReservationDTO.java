package com.bookingapp.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity

public class GuestReservationDTO {

    @Id
    private Long id;
    @Column(nullable = false)
    private Long reservationId;
    @Column(nullable = false)
    private Long accommodationId;
    @Column(nullable = false)
    private LocalDate startDate;
    @Column(nullable = false)
    private LocalDate endDate;

    public GuestReservationDTO(Long id, Long reservationId, Long accommodationId, LocalDate startDate, LocalDate endDate){
        this.id = id;
        this.reservationId = reservationId;
        this.accommodationId = accommodationId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public GuestReservationDTO() {

    }
}