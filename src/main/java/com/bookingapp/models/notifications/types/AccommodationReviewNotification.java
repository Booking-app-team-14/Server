package com.bookingapp.models.notifications.types;

import com.bookingapp.models.accommodation.Accommodation;
import com.bookingapp.models.notifications.Notification;
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
