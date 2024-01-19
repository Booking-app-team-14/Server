package com.bookingapp.dtos;

import com.bookingapp.entities.UserAccount;
import com.bookingapp.repositories.ImagesRepository;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter

public class UserInfoDTO {

    @Size(min = 1, max = 50)
    protected String firstName;
    @Size(min = 1, max = 50)
    protected String lastName;
    @NotEmpty
    protected String profilePictureType;
    @NotEmpty
    protected String profilePictureBytes;

    public UserInfoDTO(UserAccount user){

        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();

        try{
            ImagesRepository imagesRepository = new ImagesRepository();
            this.profilePictureBytes = imagesRepository.getUserImage(user.getId());
            this.profilePictureType = imagesRepository.getImageType(this.profilePictureBytes);
        } catch (Exception e) {
            this.profilePictureBytes = "";
            this.profilePictureType = "png";
        }
    }
}
