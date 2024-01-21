package com.bookingapp.entities;

import com.bookingapp.entities.Notification;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class OwnersReviewNotification extends Notification {

    @Min(value = -1)
    @Column(nullable = false)
    private int rating;

    public OwnersReviewNotification(){

    }

}
