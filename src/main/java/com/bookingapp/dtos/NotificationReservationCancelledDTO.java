package com.bookingapp.dtos;

import com.bookingapp.entities.NotificationReservationCancelled;
import com.bookingapp.entities.NotificationReservationCreated;
import com.bookingapp.entities.ReservationRequest;
import com.bookingapp.services.ReservationRequestService;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotificationReservationCancelledDTO extends NotificationDTO {

    @NotNull
    private ReservationRequestDTO reservation;

    public NotificationReservationCancelledDTO(NotificationReservationCancelled notification, ReservationRequestService reservationRequestService) {
        super(notification);
        ReservationRequest reservationRequest = reservationRequestService.getReservationRequestById(notification.getReservationRequestId());
        this.reservation = new ReservationRequestDTO(reservationRequest);
    }

}
