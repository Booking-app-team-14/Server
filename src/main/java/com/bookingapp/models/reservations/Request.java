package com.bookingapp.models.reservations;

import com.bookingapp.models.reservations.enums.Status;
import com.bookingapp.models.users.roles.Guest;
import jakarta.persistence.*;
import jakarta.websocket.OnError;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    private Guest from;
    @Column(nullable = false)
    private LocalDate startDate;
    @Column(nullable = false)
    private LocalDate endDate;
    @Column (nullable = false)
    private int numberOfGuests;
    @Column (nullable = false)
    private Status status;

    public Request(){

    }
}
