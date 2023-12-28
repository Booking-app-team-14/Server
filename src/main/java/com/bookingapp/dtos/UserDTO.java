package com.bookingapp.dtos;

import com.bookingapp.entities.UserAccount;
import com.bookingapp.enums.Role;
import com.bookingapp.repositories.ImagesRepository;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "role")
@JsonSubTypes({
        @JsonSubTypes.Type(value = GuestDTO.class, name = "GUEST"),
        @JsonSubTypes.Type(value = OwnerDTO.class, name = "OWNER"),
        @JsonSubTypes.Type(value = AdminDTO.class, name = "ADMIN")
})
@Getter
@Setter
@Component
public class UserDTO {

    private ImagesRepository imagesRepository = new ImagesRepository();

    protected String username;
    protected String password;
    protected String firstName;
    protected String lastName;
    protected String address;
    protected String phoneNumber;
    protected boolean isBlocked;
    protected boolean verified;
    protected int numberOfReports;
    protected Role role;
    protected String profilePictureType;
    protected String profilePictureBytes;

    public UserDTO(String username, String password, String firstName, String lastName, String address, String phoneNumber, Role role, boolean isBlocked, boolean verified, int numberOfReports, String profilePictureType, String profilePictureBytes){
        this.username = username;
        this.password = password;
        this.address = address;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.isBlocked = isBlocked;
        this.verified=verified;
        this.numberOfReports = numberOfReports;
        this.profilePictureType = profilePictureType;
        this.profilePictureBytes = profilePictureBytes;
    }

    public UserDTO(UserAccount user){
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.address = user.getAddress();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.role = user.getRole();
        this.phoneNumber = user.getPhoneNumber();
        this.isBlocked = user.isBlocked();
        this.verified=user.isVerified();
        this.numberOfReports = user.getNumberOfReports();
        try{
            this.profilePictureBytes = imagesRepository.getImageBytes(user.getProfilePicturePath());
            this.profilePictureType = imagesRepository.getImageType(this.profilePictureBytes);
        } catch (Exception e) {
            this.profilePictureBytes = "";
            this.profilePictureType = "png";
        }
    }

    public UserDTO(){ }

}
