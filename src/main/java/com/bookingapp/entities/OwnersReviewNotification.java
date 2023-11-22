package com.bookingapp.entities;

import com.bookingapp.entities.Notification;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class OwnersReviewNotification extends Notification {

    @Column(nullable = false)
    private int rating;

    public OwnersReviewNotification(){

    }
}
