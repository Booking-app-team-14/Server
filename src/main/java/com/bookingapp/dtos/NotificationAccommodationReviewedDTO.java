package com.bookingapp.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotificationAccommodationReviewedDTO extends NotificationDTO {

    private AccommodationReviewDTO accommodationReview;

}
