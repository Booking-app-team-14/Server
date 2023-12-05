package com.bookingapp.controllers;

import com.bookingapp.dtos.UserDTO;
import com.bookingapp.entities.UserAccount;
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

    @PostMapping(value = "/users", name = "user is registered")
    public ResponseEntity<UserDTO> registerUserAccount(@RequestBody UserDTO userDTO) {
        userAccountService.save(new UserAccount(userDTO));
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @PostMapping(value = "/users/{id}/image", name = "user uploads avatar image for his profile")
    public ResponseEntity<Long> uploadUserImage(@PathVariable Long id, @RequestBody byte[] imageBytes) {
        boolean ok = userAccountService.uploadAvatarImage(id, imageBytes);
        if (!ok) {
            return new ResponseEntity<>((long) -1, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping(value = "/users/{Id}")
    public ResponseEntity<UserDTO> getUserAccountById(@PathVariable Long Id) {
        UserAccount user = userAccountService.getUserById(Id);
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        UserDTO userDTO = new UserDTO(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
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
        boolean ok = userAccountService.deleteUser(Id);
        if (!ok) {
            return new ResponseEntity<>("Account Not Found", HttpStatus.NOT_FOUND);
        }
        userAccountService.deleteUserImage(Id);
        // TODO: if user is an owner, delete all of his properties
        return new ResponseEntity<>("Account Deleted", HttpStatus.OK);
    }

}