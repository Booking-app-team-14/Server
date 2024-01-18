package com.bookingapp.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotificationReservationRequestResponse extends Notification {

    private Long reservationRequestId;
    private Boolean approved;

}
