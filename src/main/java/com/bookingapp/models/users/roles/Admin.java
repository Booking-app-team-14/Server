package com.bookingapp.models.users.roles;

import com.bookingapp.models.users.UserAccount;
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
