package com.bookingapp.entities;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class UserReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    private UserAccount reportingUser;

    @OneToOne(fetch = FetchType.EAGER)
    private UserAccount reportedUser;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime sentAt;

    public UserReport() {

    }

    public UserReport(UserAccount reportingUser, UserAccount reportedUser, String description, LocalDateTime sentAt) {
    	this.reportingUser = reportingUser;
    	this.reportedUser = reportedUser;
    	this.description = description;
    	this.sentAt = sentAt;
    }

}
