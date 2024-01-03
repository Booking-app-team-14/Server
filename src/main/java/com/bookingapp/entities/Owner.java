package com.bookingapp.entities;

import com.bookingapp.dtos.OwnerDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Owner extends UserAccount {

    @OneToMany(cascade = {CascadeType.ALL})
    private Set<Request> reservations;

    @OneToMany(cascade = {CascadeType.ALL})
    private Set<Accommodation> accommodations;

    @JsonIgnore
    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL)
    private Set<Review> reviewsReceived;

    public Owner() {

    }

    public Owner(OwnerDTO ownerDTO) {
        this.username = ownerDTO.getUsername();
        this.password = ownerDTO.getPassword();
        this.address = ownerDTO.getAddress();
        this.firstName = ownerDTO.getFirstName();
        this.lastName = ownerDTO.getLastName();
        this.role = ownerDTO.getRole();
        this.phoneNumber = ownerDTO.getPhoneNumber();
        this.isBlocked = ownerDTO.isBlocked();
        this.numberOfReports = ownerDTO.getNumberOfReports();
        this.reservations = new HashSet<Request>();
        this.accommodations = new HashSet<Accommodation>();
    }

}
