package com.bookingapp.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotWantedNotificationsDTO {

    private boolean RESERVATION_REQUEST_CREATED;
    private boolean RESERVATION_REQUEST_CANCELLED;
    private boolean RESERVATION_REQUEST_RESPONSE;
    private boolean OWNER_REVIEWED;
    private boolean ACCOMMODATION_REVIEWED;

}
