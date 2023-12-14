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

    protected String username;
    protected String password;
    protected String firstName;
    protected String lastName;
    protected String address;
    protected String phoneNumber;
    protected boolean isBlocked;
    protected int numberOfReports;
    protected Role role;
    protected String profilePicturePath;

    public UserDTO(String username, String password, String firstName, String lastName, String address, String phoneNumber, Role role, boolean isBlocked, int numberOfReports, String profilePicturePath){
        this.username = username;
        this.password = password;
        this.address = address;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.isBlocked = isBlocked;
        this.numberOfReports = numberOfReports;
        this.profilePicturePath = profilePicturePath;
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
        this.profilePicturePath = user.getProfilePicturePath();
    }

    public UserDTO(){ }

}
