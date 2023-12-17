package com.bookingapp.dtos;

import com.bookingapp.entities.Owner;
import com.bookingapp.enums.Role;
import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class OwnerDTO extends UserDTO {

    private Set<Long> reservationsIds;
    private Set<Long> accommodationsIds;

    public OwnerDTO(String username, String password, String firstName, String lastName, String address, String phoneNumber, boolean isBlocked, boolean verified, int numberOfReports, String profilePicturePath) {
        super(username, password, firstName, lastName, address, phoneNumber, Role.OWNER, isBlocked, verified, numberOfReports, profilePicturePath);
        this.reservationsIds = new HashSet<>();
        this.accommodationsIds = new HashSet<>();
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
        this.profilePicturePath = owner.getProfilePicturePath();
        this.reservationsIds = new HashSet<>();
        this.accommodationsIds = new HashSet<>();
    }

    public OwnerDTO() { }

}
