package com.bookingapp.dtos;

import com.bookingapp.entities.Owner;
import com.bookingapp.enums.Role;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.bookingapp.repositories.ImagesRepository;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Component
public class OwnerDTO extends UserDTO {

    @NotEmpty
    private Set<Long> reservationsIds;
    @NotEmpty
    private Set<Long> accommodationsIds;
    @NotEmpty
    private Set<Long> reviewsReceivedIds;

    public OwnerDTO(String username, String password, String firstName, String lastName, String address, String phoneNumber, boolean isBlocked, boolean verified, int numberOfReports, String profilePictureType, String profilePictureBytes) {
        super(username, password, firstName, lastName, address, phoneNumber, Role.OWNER, isBlocked, verified, numberOfReports, profilePictureType, profilePictureBytes);
        this.reservationsIds = new HashSet<>();
        this.accommodationsIds = new HashSet<>();
        this.reviewsReceivedIds = new HashSet<>();

    }

    public OwnerDTO(Owner owner){
        this.setUsername(owner.getUsername());
        this.setPassword(owner.getPassword());
        this.setAddress(owner.getAddress());
        this.setFirstName(owner.getFirstName());
        this.setLastName(owner.getLastName());
        this.setRole(owner.getRole());
        this.setPhoneNumber(owner.getPhoneNumber());
        this.setBlocked(owner.isBlocked());
        this.setNumberOfReports(owner.getNumberOfReports());
        ImagesRepository imagesRepository = new ImagesRepository();
        try{
            this.profilePictureBytes = imagesRepository.getImageBytes(owner.getProfilePicturePath());
            this.profilePictureType = imagesRepository.getImageType(this.profilePictureBytes);
        } catch (Exception e) {
            this.profilePictureBytes = "";
            this.profilePictureType = "png";
        }
        this.reservationsIds = new HashSet<>();
        this.accommodationsIds = new HashSet<>();
        this.reviewsReceivedIds = new HashSet<>();
    }

    public OwnerDTO() { }

}
