package com.bookingapp.controllers;

import com.bookingapp.dtos.AdminDTO;
import com.bookingapp.dtos.GuestDTO;
import com.bookingapp.dtos.OwnerDTO;
import com.bookingapp.dtos.UserDTO;
import com.bookingapp.entities.Admin;
import com.bookingapp.entities.Guest;
import com.bookingapp.entities.Owner;
import com.bookingapp.entities.UserAccount;
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

    @PostMapping(value = "/users/guest", name = "guest is registered")
    public ResponseEntity<Long> registerGuestAccount(@RequestBody GuestDTO guestDTO) {
        Guest guest = new Guest(guestDTO);
        userAccountService.save(guest);
        return new ResponseEntity<>(guest.getId(), HttpStatus.CREATED);
    }

    @PostMapping(value = "/users/owner", name = "owner is registered")
    public ResponseEntity<Long> registerOwnerAccount(@RequestBody OwnerDTO ownerDTO) {
        Owner owner = new Owner(ownerDTO);
        userAccountService.save(owner);
        return new ResponseEntity<>(owner.getId(), HttpStatus.CREATED);
    }

    @PostMapping(value = "/users/admin", name = "admin is registered")
    public ResponseEntity<Long> registerAdminAccount(@RequestBody AdminDTO adminDTO) {
        Admin admin = new Admin(adminDTO);
        userAccountService.save(admin);
        return new ResponseEntity<>(admin.getId(), HttpStatus.CREATED);
    }

    @PutMapping(value = "/users/{Id}", name = "user updates his profile")
    public ResponseEntity<String> updateUserAccount(@PathVariable Long Id, @RequestBody UserDTO userDTO) {
        UserAccount user = userAccountService.getUserById(Id);
        if (user == null) {
            return new ResponseEntity<>("Account Not Found", HttpStatus.NOT_FOUND);
        }
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