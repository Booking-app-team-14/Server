package com.bookingapp.dtos;

import com.bookingapp.entities.Admin;
import com.bookingapp.enums.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.bookingapp.repositories.ImagesRepository;

import java.io.IOException;

@Getter
@Setter
@Component
public class AdminDTO extends UserDTO {

    public AdminDTO(String username, String password, String firstName, String lastName, String address, String phoneNumber, boolean isBlocked, boolean verified,int numberOfReports, String profilePictureType, String profilePictureBytes) {
        super(username, password, firstName, lastName, address, phoneNumber, Role.ADMIN, isBlocked, verified, numberOfReports, profilePictureType, profilePictureBytes);
    }

    public AdminDTO(Admin admin) {
        this.setUsername(admin.getUsername());
        this.setPassword(admin.getPassword());
        this.setAddress(admin.getAddress());
        this.setFirstName(admin.getFirstName());
        this.setLastName(admin.getLastName());
        this.setRole(admin.getRole());
        this.setPhoneNumber(admin.getPhoneNumber());
        this.setBlocked(admin.isBlocked());
        this.setNumberOfReports(admin.getNumberOfReports());
        ImagesRepository imagesRepository = new ImagesRepository();
        try{
            this.profilePictureBytes = imagesRepository.getImageBytes(admin.getProfilePicturePath());
            this.profilePictureType = imagesRepository.getImageType(this.profilePictureBytes);
        } catch (Exception e) {
            this.profilePictureBytes = "";
            this.profilePictureType = "png";
        }
    }

    public AdminDTO() { }

}
