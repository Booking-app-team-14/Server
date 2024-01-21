package com.bookingapp.dtos;

import com.bookingapp.entities.AccommodationReview;
import com.bookingapp.entities.NotificationAccommodationReviewed;
import com.bookingapp.services.AccommodationReviewService;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotificationAccommodationReviewedDTO extends NotificationDTO {

    @NotNull
    private AccommodationReviewDTO accommodationReview;

    public NotificationAccommodationReviewedDTO(NotificationAccommodationReviewed notification, AccommodationReviewService accommodationReviewService) {
        super(notification);
        AccommodationReview accommodationReview = accommodationReviewService.getReviewById(notification.getAccommodationReviewId()).get();
        this.accommodationReview = new AccommodationReviewDTO(accommodationReview);
    }

}
