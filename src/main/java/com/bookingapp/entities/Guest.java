package com.bookingapp.entities;

import com.bookingapp.dtos.GuestDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Guest extends UserAccount {


    @ManyToMany
    @JoinTable(
            name = "guest_favourite_accommodations",
            joinColumns = @JoinColumn(name = "guest_id"),
            inverseJoinColumns = @JoinColumn(name = "favourite_accommodations_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"guest_id", "favourite_accommodations_id"})
    )
    private Set<Accommodation> favouriteAccommodations;

    private int numberOfCancellations;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    private Set<Accommodation> history;


    @JsonIgnore
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private Set<Review> reviewsSent;

    public Guest() {

    }

    public Guest(GuestDTO guestDTO) {
        this.username = guestDTO.getUsername();
        this.password = guestDTO.getPassword();
        this.address = guestDTO.getAddress();
        this.firstName = guestDTO.getFirstName();
        this.lastName = guestDTO.getLastName();
        this.role = guestDTO.getRole();
        this.phoneNumber = guestDTO.getPhoneNumber();
        this.isBlocked = guestDTO.isBlocked();
        this.numberOfReports = guestDTO.getNumberOfReports();
        this.numberOfCancellations = guestDTO.getNumberOfCancellations();
        this.favouriteAccommodations = new HashSet<Accommodation>();
        this.history = new HashSet<Accommodation>();
    }

}
