package com.bookingapp.dtos;

import com.bookingapp.entities.OwnerReview;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OwnerReviewDTO {

    @NotNull
    private Long id;
    @NotNull
    private Long userId;
    @NotNull
    private Long ownerId;

    private Integer rating;

    private String comment;

    @NotNull
    private LocalDateTime sentAt;

    public OwnerReviewDTO(OwnerReview ownerReview) {
        this.id = ownerReview.getId();
        this.userId = ownerReview.getUser().getId();
        this.ownerId = ownerReview.getOwner().getId();
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

}
