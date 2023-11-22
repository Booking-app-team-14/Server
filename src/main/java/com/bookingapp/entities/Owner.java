package com.bookingapp.entities;

import com.bookingapp.entities.Accommodation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private Set<Accommodation> accommodations;

    //@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    //private Set<ReservationRequests> reservations;

    @Column(nullable = false)
    private int numberOfReports;

}
