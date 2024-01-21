package com.bookingapp.dtos;
import com.bookingapp.entities.Location;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class LocationDTO implements Serializable {

    @NotEmpty
    private String country;
    @NotEmpty
    private String city;
    @NotEmpty
    private String address;

    public LocationDTO(String country, String city, String address) {
        this.country = country;
        this.city = city;
        this.address = address;
    }

    public LocationDTO(Location location) {
        this.country = location.getCountry();
        this.city = location.getCity();
        this.address = location.getAddress();
    }

}
