package com.bookingapp.dtos;
import com.bookingapp.entities.Location;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationDTO {
//    private Long id;
    private String country;
    private String city;
    private String address;
    public LocationDTO(/*Long id,*/ String country, String city, String address) {
//        this.id = id;
        this.country = country;
        this.city = city;
        this.address = address;
    }

    public LocationDTO(Location location) {
//        this.id = location.getId();
        this.country = location.getCountry();
        this.city = location.getCity();
        this.address = location.getAddress();
    }
}
