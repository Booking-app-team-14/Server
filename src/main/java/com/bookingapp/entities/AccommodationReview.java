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

    @OneToOne(cascade = CascadeType.ALL)
    private Accommodation accommodation;

    @OneToOne(cascade = CascadeType.ALL)
    private UserAccount user;

    private Integer rating;

    private String comment;

    @Column(nullable = false)
    private ReviewStatus status;

    @Column(nullable = false)
    private LocalDateTime sentAt;

    public AccommodationReview() {

    }

    public AccommodationReview(Long id, Accommodation accommodation, UserAccount user, Integer rating, String comment, ReviewStatus status, LocalDateTime sentAt) {
    	this.id = id;
    	this.accommodation = accommodation;
    	this.user = user;
    	this.rating = rating;
    	this.comment = comment;
    	this.status = status;
    	this.sentAt = sentAt;
    }

}
