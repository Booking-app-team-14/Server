package com.bookingapp.dtos;

import com.bookingapp.entities.Owner;
import com.bookingapp.enums.Role;
import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class OwnerDTO {

    private Set<Long> reservationsIds;
    private Set<Long> accommodationsIds;

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private boolean isBlocked;
    private int numberOfReports;
    private Role role;

    public OwnerDTO(Owner owner) {
        this.username = owner.getUsername();
        this.password = owner.getPassword();
        this.address = owner.getAddress();
        this.firstName = owner.getFirstName();
        this.lastName = owner.getLastName();
        this.role = owner.getRole();
        this.phoneNumber = owner.getPhoneNumber();
        this.isBlocked = owner.isBlocked();
        this.numberOfReports = owner.getNumberOfReports();
        this.reservationsIds = new HashSet<>();
        this.accommodationsIds = new HashSet<>();
    }

    public OwnerDTO(String username, String password, String firstName, String lastName, String address, String phoneNumber, boolean isBlocked, int numberOfReports) {
        this.username = username;
        this.password = password;
        this.address = address;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = Role.GUEST;
        this.phoneNumber = phoneNumber;
        this.isBlocked = isBlocked;
        this.numberOfReports = numberOfReports;
        this.reservationsIds = new HashSet<>();
        this.accommodationsIds = new HashSet<>();

    }

    public OwnerDTO() { }

}
