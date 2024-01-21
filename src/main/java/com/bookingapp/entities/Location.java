package com.bookingapp.entities;


import com.bookingapp.dtos.LocationDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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

    @NotEmpty
    @Column(nullable=false)
    private String country;

    @NotEmpty
    @Column(nullable=false)
    private String city;

    @NotEmpty
    @Column(nullable=false)
    private String address;

    public Location(LocationDTO locationDTO) {
        this.country=locationDTO.getCountry();
        this.city=locationDTO.getCity();
        this.address=locationDTO.getAddress();
    }

    public Location( ) {
        this.country=country;
        this.city=city;
        this.address=address;
    }

}
