package com.bookingapp.dtos;

import com.bookingapp.entities.Guest;
import com.bookingapp.enums.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class GuestDTO {

    private Set<Long> favouriteAccommodationsIds;
    private int numberOfCancellations;
    private Set<Long> accommodationHistoryIds;

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private boolean isBlocked;
    private int numberOfReports;
    private Role role;

    public GuestDTO(Guest guest) {
        this.username = guest.getUsername();
        this.password = guest.getPassword();
        this.address = guest.getAddress();
        this.firstName = guest.getFirstName();
        this.lastName = guest.getLastName();
        this.role = guest.getRole();
        this.phoneNumber = guest.getPhoneNumber();
        this.isBlocked = guest.isBlocked();
        this.numberOfReports = guest.getNumberOfReports();
        this.numberOfCancellations = guest.getNumberOfCancellations();
        this.favouriteAccommodationsIds = new HashSet<>();
        this.accommodationHistoryIds = new HashSet<>();
    }

    public GuestDTO() { }

    public GuestDTO(String username, String password, String firstName, String lastName, String address, String phoneNumber, boolean isBlocked, int numberOfReports, int numberOfCancellations) {
        this.numberOfCancellations = 0;
        this.favouriteAccommodationsIds = new HashSet<>();
        this.accommodationHistoryIds = new HashSet<>();
        this.username = username;
        this.password = password;
        this.address = address;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = Role.GUEST;
        this.phoneNumber = phoneNumber;
        this.isBlocked = isBlocked;
        this.numberOfReports = numberOfReports;
    }

}
