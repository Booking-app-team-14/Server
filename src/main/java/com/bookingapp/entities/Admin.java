package com.bookingapp.entities;

import com.bookingapp.dtos.AdminDTO;
import com.bookingapp.entities.UserAccount;
import jakarta.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Admin extends UserAccount {

    public Admin(){

    }

    public Admin(AdminDTO adminDTO){
        this.username = adminDTO.getUsername();
        this.password = adminDTO.getPassword();
        this.address = adminDTO.getAddress();
        this.firstName = adminDTO.getFirstName();
        this.lastName = adminDTO.getLastName();
        this.role = adminDTO.getRole();
        this.phoneNumber = adminDTO.getPhoneNumber();
        this.isBlocked = adminDTO.isBlocked();
        this.numberOfReports = adminDTO.getNumberOfReports();
    }

}
