package com.bookingapp.notifications.models.types;

import com.bookingapp.notifications.models.Notification;
import com.bookingapp.reservations.models.Request;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class RequestNotification extends Notification {

    @Column(nullable = false)
    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    private Request request;

    public RequestNotification(){

    }
}
