package com.bookingapp.entities;

import com.bookingapp.entities.Accommodation;
import com.bookingapp.entities.UserAccount;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
public class Guest extends UserAccount {


    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    private Set<Accommodation> favouriteAccommodations;

    @Column(nullable = false)
    private int numberOfCancellations;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    private Set<Accommodation> history;

    @Column(nullable = false)
    private int numberOfReports;

    public Guest() {

    }
}
