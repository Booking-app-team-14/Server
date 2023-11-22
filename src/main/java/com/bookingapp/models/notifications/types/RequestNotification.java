package com.bookingapp.models.notifications.types;

import com.bookingapp.models.notifications.Notification;
import com.bookingapp.models.reservations.Request;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class RequestNotification extends Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    private Request request;

    public RequestNotification(){

    }
}
