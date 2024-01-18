package com.bookingapp.entities;

import com.bookingapp.enums.NotificationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long Id;

    @JoinColumn(nullable = false)
    @ManyToOne
    protected UserAccount sender;

    @JoinColumn(nullable = false)
    @ManyToOne
    protected UserAccount receiver;

    @Column(nullable = false)
    protected LocalDateTime sentAt;

    @Column(nullable = false)
    protected boolean seen;

    @Column(nullable = false)
    protected NotificationType type;

}
