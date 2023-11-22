package com.bookingapp.models.notifications;

import com.bookingapp.models.users.UserAccount;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    private UserAccount user;

    @Column(nullable = false)
    private int sentBefore;

    @Column(nullable = false)
    private String description;

    public Notification() {

    }
}
