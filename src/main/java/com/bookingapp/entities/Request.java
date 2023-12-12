package com.bookingapp.entities;

import com.bookingapp.dtos.RequestDTO;
import com.bookingapp.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

//    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
 //   private Guest from;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long accommodationId;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private int numberOfGuests;

    @Column(nullable = false)
    private double totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus requestStatus;

    public Request() {

    }

    public Request(RequestDTO rdto){
        this.userId = rdto.getGuestId();
        this.accommodationId = rdto.getAccommodationId();
        this.requestStatus = rdto.getRequestStatus();
        this.startDate = rdto.getStartDate();
        this.endDate = rdto.getEndDate();
        this.numberOfGuests = rdto.getNumberOfGuests();
        this.totalPrice = rdto.getTotalPrice();
    }
}
