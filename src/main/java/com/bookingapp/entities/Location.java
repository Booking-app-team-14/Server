package com.bookingapp.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
//@Embeddable
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String country;

    @Column(nullable=false)
    private String city;

    @Column(nullable=false)
    private String address;
}
