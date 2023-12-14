package com.bookingapp.entities;

import com.bookingapp.entities.Accommodation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class AccommodationRequest {

    @Id
    private Long id;

    @OneToOne
    private Accommodation accommodation;

    @OneToOne
    private Owner owner;

    private LocalDateTime dateRequested;

    private String requestType; // request type (new, updated)

    private String message;

    public AccommodationRequest(Long id, Accommodation accommodation, Owner owner, LocalDateTime dateRequested, String requestType, String message) {
        this.id = id;
        this.accommodation = accommodation;
        this.owner = owner;
        this.dateRequested = dateRequested;
        this.requestType = requestType;
        this.message = message;
    }

    public AccommodationRequest() { }

}
