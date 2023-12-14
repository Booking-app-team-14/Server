package com.bookingapp.dtos;

import com.bookingapp.entities.Guest;
import com.bookingapp.enums.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class GuestDTO extends UserDTO {

    private Set<Long> favouriteAccommodationsIds;
    private int numberOfCancellations;
    private Set<Long> accommodationHistoryIds;

    public GuestDTO(String username, String password, String firstName, String lastName, String address, String phoneNumber, boolean isBlocked, int numberOfReports, int numberOfCancellations, String profilePicturePath) {
        super(username, password, firstName, lastName, address, phoneNumber, Role.GUEST, isBlocked, numberOfReports, profilePicturePath);
        this.numberOfCancellations = numberOfCancellations;
        this.favouriteAccommodationsIds = new HashSet<>();
        this.accommodationHistoryIds = new HashSet<>();

    }

    public GuestDTO(Guest guest) {
        this.setUsername(guest.getUsername());
        this.setPassword(guest.getPassword());
        this.setAddress(guest.getAddress());
        this.setFirstName(guest.getFirstName());
        this.setLastName(guest.getLastName());
        this.setRole(guest.getRole());
        this.setPhoneNumber(guest.getPhoneNumber());
        this.setBlocked(guest.isBlocked());
        this.setNumberOfReports(guest.getNumberOfReports());
        this.numberOfCancellations = guest.getNumberOfCancellations();
        this.favouriteAccommodationsIds = new HashSet<>();
        this.accommodationHistoryIds = new HashSet<>();
    }

    public GuestDTO() { }

}