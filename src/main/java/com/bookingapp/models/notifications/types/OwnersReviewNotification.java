package com.bookingapp.models.notifications.types;

import com.bookingapp.models.notifications.Notification;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class OwnersReviewNotification extends Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private int rating;

    public OwnersReviewNotification(){

    }
}
