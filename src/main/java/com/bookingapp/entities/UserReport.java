package com.bookingapp.entities;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private UserAccount reportingUser;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private UserAccount reportedUser;

    @NotEmpty
    @Column(nullable = false)
    private String description;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime sentAt;

    public UserReport() {

    }

    public UserReport(UserAccount reportingUser, UserAccount reportedUser, String description) {
    	this.reportingUser = reportingUser;
    	this.reportedUser = reportedUser;
    	this.description = description;
    	this.sentAt = LocalDateTime.now();
    }

}
