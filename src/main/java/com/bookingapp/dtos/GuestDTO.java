package com.bookingapp.dtos;

import com.bookingapp.entities.Guest;
import com.bookingapp.entities.Accommodation;
import com.bookingapp.enums.Role;
import com.bookingapp.repositories.ImagesRepository;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Component
public class GuestDTO extends UserDTO {

    @NotEmpty
    private Set<Long> favouriteAccommodationsIds;
    @Min(value = 0)
    private int numberOfCancellations;
    @NotEmpty
    private Set<Long> accommodationHistoryIds;
    @NotEmpty
    private Set<Long> reviewsSentIds;

    public GuestDTO(String username, String password, String firstName, String lastName, String address, String phoneNumber, boolean isBlocked, boolean verified, int numberOfReports, int numberOfCancellations, String profilePictureType, String profilePictureBytes) {
        super(username, password, firstName, lastName, address, phoneNumber, Role.GUEST, isBlocked, verified, numberOfReports, profilePictureType, profilePictureBytes);
        this.numberOfCancellations = numberOfCancellations;
        this.favouriteAccommodationsIds = new HashSet<>();
        this.accommodationHistoryIds = new HashSet<>();
        this.reviewsSentIds = new HashSet<>();
    }

    public GuestDTO(Guest guest) {
        this.setUsername(guest.getUsername());
        this.setPassword(guest.getPassword());
        this.setAddress(guest.getAddress());
        this.setFirstName(guest.getFirstName());
        this.setLastName(guest.getLastName());
        this.setRole(guest.getRole());
        this.setPhoneNumber(guest.getPhoneNumber());
        this.setBlocked(guest.isBlocked());
        this.setNumberOfReports(guest.getNumberOfReports());
        ImagesRepository imagesRepository = new ImagesRepository();
        try{
            this.profilePictureBytes = imagesRepository.getImageBytes(guest.getProfilePicturePath());
            this.profilePictureType = imagesRepository.getImageType(this.profilePictureBytes);
        } catch (Exception e) {
            this.profilePictureBytes = "";
            this.profilePictureType = "png";
        }
        this.numberOfCancellations = guest.getNumberOfCancellations();
        this.favouriteAccommodationsIds = guest.getFavouriteAccommodations().stream()
                .map(Accommodation::getId)
                .collect(Collectors.toSet());

        this.accommodationHistoryIds = new HashSet<>();
        this.reviewsSentIds = new HashSet<>();
    }

    public GuestDTO() { }

}