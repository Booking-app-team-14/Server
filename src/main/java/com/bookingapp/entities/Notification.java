package com.bookingapp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    private UserAccount user;

    @Column(nullable = false)
    private int sentBefore;

    @Column(nullable = false)
    private String description;

    public Notification() {

    }
}
