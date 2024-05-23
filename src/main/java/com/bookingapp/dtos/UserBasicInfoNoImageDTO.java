package com.bookingapp.dtos;

import com.bookingapp.entities.UserAccount;

public class UserBasicInfoNoImageDTO {

    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;

    public UserBasicInfoNoImageDTO() { }

    public UserBasicInfoNoImageDTO(UserAccount userAccount) {
        this.firstName = userAccount.getFirstName();
        this.lastName = userAccount.getLastName();
        this.address = userAccount.getAddress();
        this.phoneNumber = userAccount.getPhoneNumber();
    }

    public UserBasicInfoNoImageDTO(String firstName, String lastName, String address, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
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

}
