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

   /* public ReservationRequestNotificationDTO(Long senderId, Long receiverId, String sentAt, boolean seen, String type) {
         super(senderId, receiverId, sentAt, seen, type);
        this.accommodationId = accommodationId;
    }*/

}