package com.bookingapp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Reservation {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany
    private Set<Availability> availability;

    @ManyToOne
    private Accommodation accommodation;

    public Reservation() {
        this.availability = new HashSet<>();
    }

    public Reservation(Set<Availability> availability) {
        this.availability = availability;
    }

}
