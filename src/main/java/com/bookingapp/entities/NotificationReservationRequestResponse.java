package com.bookingapp.entities;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class NotificationReservationRequestResponse extends Notification {

    @NotNull
    private Long reservationRequestId;
    @NotNull
    private Boolean approved;

}
