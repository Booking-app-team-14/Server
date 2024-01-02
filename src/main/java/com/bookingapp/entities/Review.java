package com.bookingapp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String comment;


    @Column(nullable = false)
    private int rating;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private boolean reported;

    @Column(nullable = false)
    private boolean approved;

    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Guest sender;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner recipient;

    public Review() {
        this.timestamp = LocalDateTime.now();
    }
    public Review(String comment, int rating, Guest sender, Owner recipient) {
        this.comment = comment;
        this.rating = rating;
        this.sender = sender;
        this.recipient = recipient;
        this.timestamp = LocalDateTime.now();
        this.reported = false;
        this.approved = false;
    }
}
