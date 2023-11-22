package com.bookingapp.models.users.roles;

import com.bookingapp.models.accommodation.Accommodation;
import com.bookingapp.models.users.UserAccount;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;
@Getter
@Setter
@Entity
public class Guest extends UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    private Set<Accommodation> favouriteAccommodations;
    @Column(nullable = false)
    private int numberOfCancellations;
    @Column(nullable = false)
    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    private Set<Accommodation> history;
    @Column(nullable = false)
    private int numberOfReports;

    public Guest(){

    }
}
