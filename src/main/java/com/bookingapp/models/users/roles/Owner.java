package com.bookingapp.models.users.roles;

import com.bookingapp.models.accommodation.Accommodation;
import com.bookingapp.models.reservations.Request;
import com.bookingapp.models.users.UserAccount;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class Owner extends UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    private Set<Request> reservations;
    @Column(nullable = false)
    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    private Set<Accommodation> accomodations;
    @Column(nullable = false)
    private int numberOfReports;

    public Owner(){

    }
}
