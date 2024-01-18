package com.bookingapp.dtos;

import com.bookingapp.entities.Notification;
import com.bookingapp.enums.NotificationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class NotificationDTO {

    private Long senderId;
    private Long receiverId;
    private String sentAt; // epoch seconds
    private boolean seen;
    private NotificationType type;

    public NotificationDTO(Notification notification) {
        this.senderId = notification.getSender().getId();
        this.receiverId = notification.getReceiver().getId();
        this.sentAt = notification.getSentAt().toString();
        this.seen = notification.isSeen();
        this.type = notification.getType();
    }

}

