package com.bookingapp.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotificationAccommodationReviewed extends Notification {

    private Long accommodationReviewId;

}
