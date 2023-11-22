package com.bookingapp.users.models.roles;

import com.bookingapp.accommodation.Accommodation;
import com.bookingapp.users.models.UserAccount;
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
