package com.bookingapp.entities;


import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Getter
@Setter
@Entity
public class UserAccount {

    @Id
    private Long id;

    private String username;

    private String imagePath;

    private Integer numberOfReports;

    private Boolean blocked;

    public UserAccount() {

    }

    public UserAccount(Long id, String username, String imagePath, Integer numberOfReports, Boolean blocked) {
    	this.id = id;
    	this.username = username;
    	this.imagePath = imagePath;
    	this.numberOfReports = numberOfReports;
        this.blocked = blocked;
    }

}
