package com.bookingapp.dtos;

import com.bookingapp.entities.Admin;
import com.bookingapp.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminDTO extends UserDTO {

    public AdminDTO(String username, String password, String firstName, String lastName, String address, String phoneNumber, boolean isBlocked, int numberOfReports, String profilePicturePath) {
        super(username, password, firstName, lastName, address, phoneNumber, Role.ADMIN, isBlocked, numberOfReports, profilePicturePath);
    }

    public AdminDTO(Admin admin){
        this.setUsername(admin.getUsername());
        this.setPassword(admin.getPassword());
        this.setAddress(admin.getAddress());
        this.setFirstName(admin.getFirstName());
        this.setLastName(admin.getLastName());
        this.setRole(admin.getRole());
        this.setPhoneNumber(admin.getPhoneNumber());
        this.setBlocked(admin.isBlocked());
        this.setNumberOfReports(admin.getNumberOfReports());
        this.setProfilePicturePath(admin.getProfilePicturePath());
    }

    public AdminDTO() { }

}
