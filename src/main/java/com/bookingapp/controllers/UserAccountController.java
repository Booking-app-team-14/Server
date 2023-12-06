package com.bookingapp.controllers;

import com.bookingapp.dtos.AdminDTO;
import com.bookingapp.dtos.GuestDTO;
import com.bookingapp.dtos.OwnerDTO;
import com.bookingapp.dtos.UserDTO;
import com.bookingapp.entities.*;
import com.bookingapp.enums.Role;
import com.bookingapp.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserAccountController {

    @Autowired
    private UserAccountService userAccountService;

    @PostMapping(value = "/users", name = "register user") // api/users?type=GUEST
    public ResponseEntity<Long> registerUserAccount(@RequestBody UserDTO userDTO, @RequestParam("type") Role role) {
        UserAccount user = switch (role) {
            case GUEST -> new Guest((GuestDTO) userDTO);
            case OWNER -> new Owner((OwnerDTO) userDTO);
            case ADMIN -> new Admin((AdminDTO) userDTO);
            default -> null;
        };
        if (user == null) {
            return new ResponseEntity<>((long) -1, HttpStatus.BAD_REQUEST);
        }
        user.setRole(role);
        userAccountService.save(user);
        return new ResponseEntity<>(user.getId(), HttpStatus.CREATED);
    }

    @PutMapping(value = "/users/{id}", name = "user updates his profile")
    public ResponseEntity<String> updateUserAccount(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        UserAccount user = userAccountService.getUserById(id);
        if (user == null) {
            return new ResponseEntity<>("Account Not Found", HttpStatus.NOT_FOUND);
        }
//        This is how we can update the user's special fields depending on his role
//        switch (userDTO.getRole()) {
//            case GUEST:
//                GuestDTO guestDTO = (GuestDTO) userDTO;
//                Guest g = (Guest) userAccountService.getUserById(id);
//                Set<Accommodation> favouriteAccommodations = new HashSet<Accommodation>();
//                for (Long accommodationId : guestDTO.getFavouriteAccommodationsIds()) {
//                    Accommodation accommodation = accommodatiomService.getAccommodationById(accommodationId);
//                    if (accommodation != null) {
//                        favouriteAccommodations.add(accommodation);
//                    }
//                }
//                g.setFavouriteAccommodations(favouriteAccommodations);
//                // ...
//                break;
//            case OWNER:
//                OwnerDTO ownerDTO = (OwnerDTO) userDTO;
//                Owner o = (Owner) userAccountService.getUserById(id);
//                // ...
//                break;
//            case ADMIN:
//                break;
//        }
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setAddress(userDTO.getAddress());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        userAccountService.save(user);
        return new ResponseEntity<>("Account Updated", HttpStatus.OK);
    }

    @GetMapping(value = "/users/{Id}")
    public ResponseEntity<?> getUserAccountById(@PathVariable Long Id) {
        UserAccount user = userAccountService.getUserById(Id);
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        if (user.getRole() == Role.GUEST) {
            Guest guest = (Guest) user;
            return new ResponseEntity<>(new GuestDTO(guest), HttpStatus.OK);
        }
        else if (user.getRole() == Role.OWNER) {
            Owner owner = (Owner) user;
            return new ResponseEntity<>(new OwnerDTO(owner), HttpStatus.OK);
        }
        else {
            Admin admin = (Admin) user;
            return new ResponseEntity<>(new AdminDTO(admin), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/users/{id}/image", name = "user uploads avatar image for his profile")
    public ResponseEntity<Long> uploadUserImage(@PathVariable Long id, @RequestBody byte[] imageBytes) {
        boolean ok = userAccountService.uploadAvatarImage(id, imageBytes);
        if (!ok) {
            return new ResponseEntity<>((long) -1, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping(value = "/users/{Id}/image")
    public ResponseEntity<byte[]> getUserImage(@PathVariable Long Id) {
        byte[] imageBytes = userAccountService.getUserImage(Id);
        if (imageBytes == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(imageBytes, HttpStatus.OK);
    }

    @DeleteMapping("/users/{Id}")
    public ResponseEntity<String> deleteUserAccount(@PathVariable Long Id) {
        // TODO check if guest or owner have any active requests, if yes, don't allow delete
        UserAccount userAccount = userAccountService.getUserById(Id);
        if (userAccount == null) {
            return new ResponseEntity<>("Account Not Found", HttpStatus.NOT_FOUND);
        }
        if (userAccount.getRole() == Role.GUEST) {
            Guest guest = (Guest) userAccount;
             // check if guest has active requests
//            return new ResponseEntity<>("Guest has active requests", HttpStatus.BAD_REQUEST);
        }
        else if (userAccount.getRole() == Role.OWNER) {
            Owner owner = (Owner) userAccount;
            // check if owner has active requests
//            return new ResponseEntity<>("Owner has active requests", HttpStatus.BAD_REQUEST);
        }

        userAccountService.deleteUser(Id);
        userAccountService.deleteUserImage(Id);

        return new ResponseEntity<>("Account Deleted", HttpStatus.OK);
    }

}