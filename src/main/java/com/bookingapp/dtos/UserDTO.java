package com.bookingapp.dtos;

import com.bookingapp.entities.UserAccount;
import com.bookingapp.enums.Role;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "role")
@JsonSubTypes({
        @JsonSubTypes.Type(value = GuestDTO.class, name = "GUEST"),
        @JsonSubTypes.Type(value = OwnerDTO.class, name = "OWNER"),
        @JsonSubTypes.Type(value = AdminDTO.class, name = "ADMIN")
})
@Getter
@Setter
public class UserDTO {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private boolean isBlocked;
    private int numberOfReports;
    private Role role;

    public UserDTO(String username, String password, String firstName, String lastName, String address, String phoneNumber, Role role, boolean isBlocked, int numberOfReports){
        this.username = username;
        this.password = password;
        this.address = address;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.isBlocked = isBlocked;
        this.numberOfReports = numberOfReports;
    }

    public UserDTO(UserAccount user){
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.address = user.getAddress();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.role = user.getRole();
        this.phoneNumber = user.getPhoneNumber();
        this.isBlocked = user.isBlocked();
        this.numberOfReports = user.getNumberOfReports();
    }

    public UserDTO(){ }

}
