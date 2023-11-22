package com.bookingapp.entities;

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
