package com.bookingapp.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.Base64;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class AccommodationUpdateDTO implements Serializable {

    @NotNull
    private Long id;
    @NotNull
    private Set<Image> images;
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @NotEmpty
    private String type;
    @Min(value = 1)
    private Integer minNumberOfGuests;
    @Min(value = 1)
    private Integer maxNumberOfGuests;
    @NotNull
    private Set<Long> amenities;
    @NotNull
    private LocationDTO location;
    private boolean pricePerGuest;
    @Min(value = 1)
    private Double defaultPrice;
    @NotNull
    private Set<UpdateAvailabilityDTO> availability;
    @Min(value = 0)
    private Integer cancellationDeadline;
    @NotEmpty
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
