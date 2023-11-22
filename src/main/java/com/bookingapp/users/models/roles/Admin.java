package com.bookingapp.users.models.roles;

import com.bookingapp.users.models.UserAccount;
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
