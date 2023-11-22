package com.bookingapp.users.models.roles;

import com.bookingapp.accommodation.Accommodation;
import com.bookingapp.reservations.models.Request;
import com.bookingapp.users.models.UserAccount;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
public class Owner extends UserAccount {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    private Set<Request> reservations;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    private Set<Accommodation> accommodations;

    @Column(nullable = false)
    private int numberOfReports;

    public Owner() {

    }
}
