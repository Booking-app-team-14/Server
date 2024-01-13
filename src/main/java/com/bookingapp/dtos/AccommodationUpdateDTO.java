package com.bookingapp.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.Base64;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class AccommodationUpdateDTO implements Serializable {

    private Long id;
    private Set<Image> images;
    private String name;
    private String description;
    private String type;
    private Integer minNumberOfGuests;
    private Integer maxNumberOfGuests;
    private Set<Long> amenities;
    private LocationDTO location;
    private boolean pricePerGuest;
    private Double defaultPrice;
    private Set<UpdateAvailabilityDTO> availability;
    private Integer cancellationDeadline;
    private String message;
    private boolean automaticHandling;

    public AccommodationUpdateDTO(Set<Image> images, String name, String description, String type, Integer minNumberOfGuests, Integer maxNumberOfGuests, Set<Long> amenities, LocationDTO location, boolean pricePerGuest, Double defaultPrice, Set<UpdateAvailabilityDTO> availability, Integer cancellationDeadline, String message, boolean automaticHandling) {
        this.images = images;
        this.name = name;
        this.description = description;
        this.type = type;
        this.minNumberOfGuests = minNumberOfGuests;
        this.maxNumberOfGuests = maxNumberOfGuests;
        this.amenities = amenities;
        this.location = location;
        this.pricePerGuest = pricePerGuest;
        this.defaultPrice = defaultPrice;
        this.availability = availability;
        this.cancellationDeadline = cancellationDeadline;
        this.message = message;
        this.automaticHandling = automaticHandling;
    }

    public AccommodationUpdateDTO() {
    }

    public boolean getPricePerGuest() {
        return this.pricePerGuest;
    }

    public String serializeToString() {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                oos.writeObject(this);
                return Base64.getEncoder().encodeToString(baos.toByteArray());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static AccommodationUpdateDTO deserializeFromString(String serializedString) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(Base64.getDecoder().decode(serializedString));
             ObjectInputStream ois = new ObjectInputStream(bais)) {
                Object object = ois.readObject();
                if (object instanceof AccommodationUpdateDTO) return (AccommodationUpdateDTO) object;
                else throw new RuntimeException("Deserialized object is not of type AccommodationUpdateDTO");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
