package com.bookingapp.dtos;

import com.bookingapp.dtos.NotificationDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ReservationRequestNotificationDTO extends NotificationDTO {

    private Long accommodationId;

}
