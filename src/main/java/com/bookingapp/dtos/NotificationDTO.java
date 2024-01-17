package com.bookingapp.dtos;

import com.bookingapp.entities.Notification;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class NotificationDTO {

    private Long senderId;
    private Long receiverId;
    private String sentAt; // epoch seconds
    private boolean seen;
    private String type; // TODO: make enum, not string

    public NotificationDTO() {

    }

   /* public NotificationDTO(Long senderId, Long receiverId, String sentAt, boolean seen, String type) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.sentAt = sentAt;
        this.seen = seen;
        this.type = type;
    }*/

    /*public NotificationDTO(Notification notification) {
        this.senderId = notification.getSender().getId();
        this.receiverId = notification.getReceiver().getId();
        this.sentAt = notification.getSentAt().toString();
        this.seen = notification.isSeen();
        this.type = notification.getType();
    }*/

}

