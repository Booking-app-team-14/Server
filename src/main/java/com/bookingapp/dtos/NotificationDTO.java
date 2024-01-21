package com.bookingapp.dtos;

import com.bookingapp.entities.Notification;
import com.bookingapp.enums.NotificationType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class NotificationDTO {

    @NotNull
    private Long id;
    @NotNull
    private Long senderId;
    @NotNull
    private Long receiverId;
    @NotEmpty
    private String sentAt; // epoch seconds
    private boolean seen;
    @NotNull
    private NotificationType type;

    public NotificationDTO(Notification notification) {
        this.id = notification.getId();
        this.senderId = notification.getSender().getId();
        this.receiverId = notification.getReceiver().getId();
        this.sentAt = notification.getSentAt().toString();
        this.seen = notification.isSeen();
        this.type = notification.getType();
    }

}

