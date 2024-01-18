package com.bookingapp.dtos;

import com.bookingapp.entities.NotificationReservationRequestResponse;
import com.bookingapp.entities.ReservationRequest;
import com.bookingapp.services.ReservationRequestService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotificationReservationRequestResponseDTO extends NotificationDTO {

    private ReservationRequestDTO reservation;
    private Boolean accepted;

    public NotificationReservationRequestResponseDTO(NotificationReservationRequestResponse notification, ReservationRequestService reservationRequestService) {
        super(notification);
        ReservationRequest reservationRequest = reservationRequestService.getReservationRequestById(notification.getReservationRequestId());
        this.reservation = new ReservationRequestDTO(reservationRequest, reservationRequestService.getUserAccountService(), reservationRequestService.getAccommodationService());
        this.accepted = notification.getApproved();
    }

}
