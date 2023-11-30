package com.bookingapp.dtos;

import com.bookingapp.entities.Notification;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationDTO {
    private Long userId;
    private int sentBefore;
    private String description;

    public NotificationDTO(Long userId, int sentBefore, String description){
        this.userId = userId;
        this.sentBefore = sentBefore;
        this.description = description;
    }

    public NotificationDTO() {

    }

    public NotificationDTO(Notification n) {
        this.userId = n.getUser().getId();
        this.description = n.getDescription();
        this.sentBefore = n.getSentBefore();
    }

}

