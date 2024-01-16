package com.bookingapp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @JoinColumn(nullable = false)
    @ManyToOne
    private UserAccount sender;

    @JoinColumn(nullable = false)
    @ManyToOne
    private UserAccount receiver;

    @Column(nullable = false)
    private LocalDate sentAt;

    @Column(nullable = false)
    private boolean seen;

    // TODO: enum not string
    @Column(nullable = false)
    private String type;

    public Notification() {

    }

}
