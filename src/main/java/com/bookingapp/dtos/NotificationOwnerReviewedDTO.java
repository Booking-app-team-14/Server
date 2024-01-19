package com.bookingapp.dtos;

import com.bookingapp.entities.NotificationOwnerReviewed;
import com.bookingapp.entities.Review;
import com.bookingapp.services.ReviewService;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotificationOwnerReviewedDTO extends NotificationDTO {

    @NotNull
    private ReviewDTO review;

    public NotificationOwnerReviewedDTO(NotificationOwnerReviewed notification, ReviewService reviewService) {
        super(notification);
        Review review = reviewService.getReviewById(notification.getReviewId()).get();
        this.review = new ReviewDTO(review);
    }

}
