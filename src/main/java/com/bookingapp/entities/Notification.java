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
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @JoinColumn(nullable = false)
    @ManyToOne
    private UserAccount sender;

    @JoinColumn(nullable = false)
    @ManyToOne
    private UserAccount receiver;

    @Column(nullable = false)
    private LocalDateTime sentAt;

    @Column(nullable = false)
    private boolean seen;

    @Column(nullable = false)
    private NotificationType type;

}
