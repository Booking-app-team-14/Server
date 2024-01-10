package com.bookingapp.dtos;

import com.bookingapp.entities.Review;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewDTO {

    private Long id;
    private String comment;
    private int rating;
    private LocalDateTime timestamp;
    private boolean reported;
    private boolean approved;
    private Long senderId;
    private Long recipientId;

    public ReviewDTO() {
        this.timestamp = LocalDateTime.now();
    }
    public ReviewDTO(String comment, int rating, Long senderId, Long recipientId) {
        this.comment = comment;
        this.rating = rating;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.timestamp = LocalDateTime.now();
        this.reported = false;
        this.approved = false;
    }
    public ReviewDTO(Review review) {
        this.id = review.getId();
        this.comment = review.getComment();
        this.rating = review.getRating();
        this.timestamp = review.getTimestamp();
        this.reported = review.isReported();
        this.approved = review.isApproved();
        this.senderId = review.getSender().getId();
        this.recipientId = review.getRecipient().getId();
    }
}

