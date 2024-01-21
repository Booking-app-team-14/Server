package com.bookingapp.dtos;

import com.bookingapp.entities.NotificationReservationRequestResponse;
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
public class NotificationReservationRequestResponseDTO extends NotificationDTO {

    @NotNull
    private ReservationRequestDTO reservation;
    @NotNull
    private Boolean accepted;

    public NotificationReservationRequestResponseDTO(NotificationReservationRequestResponse notification, ReservationRequestService reservationRequestService) {
        super(notification);
        ReservationRequest reservationRequest = reservationRequestService.getReservationRequestById(notification.getReservationRequestId());
        this.reservation = new ReservationRequestDTO(reservationRequest);
        this.accepted = notification.getApproved();
    }

}
