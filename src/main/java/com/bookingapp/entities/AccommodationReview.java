package com.bookingapp.entities;

import com.bookingapp.enums.ReviewStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class AccommodationReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Accommodation accommodation;

    @ManyToOne
    private Guest user;

    private Integer rating;

    private String comment;

    @Column(nullable = false)
    private ReviewStatus status;

    @Column(nullable = false)
    private LocalDateTime sentAt;

    public AccommodationReview() {

    }

    public AccommodationReview(Accommodation accommodation, Guest user, Integer rating, String comment, ReviewStatus status, LocalDateTime sentAt) {
    	//this.id = id;
    	this.accommodation = accommodation;
    	this.user = user;
    	this.rating = rating;
    	this.comment = comment;
    	this.status = status;
    	this.sentAt = sentAt;
    }

}
