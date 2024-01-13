package com.bookingapp.dtos;

import com.bookingapp.entities.UserAccount;
import com.bookingapp.repositories.ImagesRepository;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter

public class UserInfoDTO {

    private ImagesRepository imagesRepository = new ImagesRepository();

    protected String firstName;
    protected String lastName;
    protected String profilePictureType;
    protected String profilePictureBytes;

    public UserInfoDTO(UserAccount user){

        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();

        try{
            //this.profilePictureBytes = userService.getUserImage(user.getId());
            this.profilePictureBytes = imagesRepository.getUserImage(user.getId());
            this.profilePictureType = imagesRepository.getImageType(this.profilePictureBytes);
        } catch (Exception e) {
            this.profilePictureBytes = "";
            this.profilePictureType = "png";
        }
    }
}
