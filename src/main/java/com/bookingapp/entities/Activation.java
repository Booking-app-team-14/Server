package com.bookingapp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Activation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    private UserAccount user;

    private LocalDateTime creationDate;

    private LocalDateTime expirationDate;


    //da li je aktivacija istekla
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expirationDate);
    }

}
