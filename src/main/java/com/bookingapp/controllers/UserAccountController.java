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

    @PostMapping(value = "/users/{id}/images", name = "user uploads avatar image for his profile")
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
        UserDTO userDTO = new UserDTO(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

//    If user get "deleted" he is essentially just blocked, which is on another endpoint
//    @DeleteMapping("/users/{Id}")
//    public ResponseEntity<String> deleteUserAccount(@PathVariable Long Id) {
//        return new ResponseEntity<>("Deleted", HttpStatus.OK);
//    }

}