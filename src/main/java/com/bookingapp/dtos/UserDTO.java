package com.bookingapp.dtos;

import com.bookingapp.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private Role role;
    private boolean isBlocked;
    private String imagePath;
    private int numberOfReports;

    public UserDTO(String username, String password, String firstName, String lastName, String address, String phoneNumber, Role role, boolean isBlocked, String imagePath, int numberOfReports){
        this.username = username;
        this.password = password;
        this.address = address;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.isBlocked = isBlocked;
        this.imagePath = imagePath;
        this.numberOfReports = numberOfReports;
    }
    public UserDTO(){

    }
}
