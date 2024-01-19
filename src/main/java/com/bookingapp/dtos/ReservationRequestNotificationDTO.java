package com.bookingapp.dtos;

import com.bookingapp.dtos.NotificationDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ReservationRequestNotificationDTO extends NotificationDTO {

    @NotNull
    private Long accommodationId;

}
