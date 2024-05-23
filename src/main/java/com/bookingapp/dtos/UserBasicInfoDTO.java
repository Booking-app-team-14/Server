package com.bookingapp.dtos;

import com.bookingapp.entities.UserAccount;

public class UserBasicInfoDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String phoneNumber;
    private String profilePictureBytes;

    public UserBasicInfoDTO() { }

    public UserBasicInfoDTO(UserAccount userAccount) {
        this.firstName = userAccount.getFirstName();
        this.lastName = userAccount.getLastName();
        this.email = userAccount.getUsername();
        this.address = userAccount.getAddress();
        this.phoneNumber = userAccount.getPhoneNumber();
        this.profilePictureBytes = "";
    }

    public UserBasicInfoDTO(String firstName, String lastName, String email, String address, String phoneNumber, String profilePictureBytes) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.profilePictureBytes = profilePictureBytes;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfilePictureBytes() {
        return profilePictureBytes;
    }

    public void setProfilePictureBytes(String profilePictureBytes) {
        this.profilePictureBytes = profilePictureBytes;
    }

}
