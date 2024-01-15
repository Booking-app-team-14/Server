package com.bookingapp.dtos;

import com.bookingapp.entities.Notification;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class NotificationDTO {
    private Long from;
    private Long to;
    private LocalDate sentBefore;
    private String description;

    public NotificationDTO(Long from ,Long to, LocalDate sentBefore, String description){
        this.from = from;
        this.to = to;
        this.sentBefore = sentBefore;
        this.description = description;
    }

    public NotificationDTO() {

    }

    public NotificationDTO(Notification n) {
        this.to = n.getUser().getId();
        this.description = n.getDescription();
        this.sentBefore = n.getSentBefore();
    }

}

