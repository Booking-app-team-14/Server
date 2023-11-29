package com.bookingapp.dtos;

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
}

