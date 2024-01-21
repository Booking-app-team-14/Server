package com.bookingapp.entities;

import com.bookingapp.enums.NotificationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @JoinColumn(nullable = false)
    @ManyToOne
    protected UserAccount sender;

    @NotNull
    @JoinColumn(nullable = false)
    @ManyToOne
    protected UserAccount receiver;

    @NotNull
    @Column(nullable = false)
    protected LocalDateTime sentAt;

    @Column(nullable = false)
    protected boolean seen;

    @NotNull
    @Column(nullable = false)
    protected NotificationType type;

}
