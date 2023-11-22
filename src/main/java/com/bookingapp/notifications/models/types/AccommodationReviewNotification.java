package com.bookingapp.notifications.models.types;

import com.bookingapp.accommodation.Accommodation;
import com.bookingapp.notifications.models.Notification;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class AccommodationReviewNotification extends Notification {

    @Column(nullable = false)
    private int rating;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    private Accommodation accommodation;

    public AccommodationReviewNotification() {

    }
}
