package com.bookingapp.dtos;

import com.bookingapp.entities.Admin;
import com.bookingapp.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminDTO {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private boolean isBlocked;
    private int numberOfReports;
    private Role role;

    public AdminDTO(Admin admin) {
        this.username = admin.getUsername();
        this.password = admin.getPassword();
        this.address = admin.getAddress();
        this.firstName = admin.getFirstName();
        this.lastName = admin.getLastName();
        this.role = admin.getRole();
        this.phoneNumber = admin.getPhoneNumber();
        this.isBlocked = admin.isBlocked();
        this.numberOfReports = admin.getNumberOfReports();
    }

    public AdminDTO(String username, String password, String firstName, String lastName, String address, String phoneNumber, boolean isBlocked, int numberOfReports) {
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

    public AdminDTO() { }

}
