package com.bookingapp.entities;

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
}
