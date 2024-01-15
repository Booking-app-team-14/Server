package com.bookingapp.dtos;

import com.bookingapp.dtos.NotificationDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ReservationRequestNotificationDTO extends NotificationDTO {
    private LocalDate sentBefore;
    private Long accommodationId;

    public ReservationRequestNotificationDTO(Long from, Long to, LocalDate sentBefore, String description, Long accommodationId) {
        super(from, to, sentBefore, description);
        this.accommodationId = accommodationId;
    }

}
