package com.bookingapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
public class Accommodation {
    @Id
    private Long id;
    public Accommodation(){

    }

}
