package com.bookingapp.dtos;

import com.bookingapp.entities.OwnerReview;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OwnerReviewDTO {

    private Long id;

    private Long userId;

    private Long ownerId;

    private Integer rating;

    private String comment;

    private LocalDateTime sentAt;

    public OwnerReviewDTO(OwnerReview ownerReview) {
        this.id = ownerReview.getId();
        this.userId = ownerReview.getUser().getUserId();
        this.ownerId = ownerReview.getOwner().getUserId();
        this.rating = ownerReview.getRating();
        this.comment = ownerReview.getComment();
        this.sentAt = ownerReview.getSentAt();
    }

    public OwnerReviewDTO(Long id, Long userId, Long ownerId, Integer rating, String comment, LocalDateTime sentAt) {
        this.id = id;
        this.userId = userId;
        this.ownerId = ownerId;
        this.rating = rating;
        this.comment = comment;
        this.sentAt = sentAt;
    }

    public OwnerReviewDTO() {
    }

    @Override
    public String toString() {
        return "test";
    }
}
