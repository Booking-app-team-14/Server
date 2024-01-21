package com.bookingapp.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class AccommodationReviewNotification extends Notification {

    @Min(value = -1)
    @Column(nullable = false)
    private int rating;

    @NotNull
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    private Accommodation accommodation;

    public AccommodationReviewNotification() {
    }

}
