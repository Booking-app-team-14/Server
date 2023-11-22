package com.bookingapp.dtos;

import com.bookingapp.entities.AccommodationReview;
import com.bookingapp.enums.ReviewStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AccommodationReviewDTO {

    private Long id;

    private Long accommodationId;

    private Long userId;

    private Integer rating;

    private String comment;

    private ReviewStatus status;

    private LocalDateTime sentAt;

    public AccommodationReviewDTO(AccommodationReview accommodationReview) {
        this.id = accommodationReview.getId();
        this.accommodationId = accommodationReview.getAccommodation().getId();
        this.userId = accommodationReview.getUser().getId();
        this.rating = accommodationReview.getRating();
        this.comment = accommodationReview.getComment();
        this.status = accommodationReview.getStatus();
        this.sentAt = accommodationReview.getSentAt();
    }

    public AccommodationReviewDTO(Long id, Long accommodationId, Long userId, Integer rating, String comment, ReviewStatus status, LocalDateTime sentAt) {
        this.id = id;
        this.accommodationId = accommodationId;
        this.userId = userId;
        this.rating = rating;
        this.comment = comment;
        this.status = status;
        this.sentAt = sentAt;
    }

}
