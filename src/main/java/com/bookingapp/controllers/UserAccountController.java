package com.bookingapp.controllers;

import com.bookingapp.dtos.UserDTO;
import com.bookingapp.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserAccountController {

    private final UserAccountService userAccountService;
    @Autowired
    public UserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }


    @PostMapping(value = "/users/{Id}}", name = "user is registered")
    public ResponseEntity<UserDTO> createUserAccount() {
        return new ResponseEntity<>(new UserDTO(), HttpStatus.CREATED);
    }

    @GetMapping(value = "/users/{Id}", name = "for view of the profile")
    public ResponseEntity<UserDTO> getUserAccountById(@PathVariable Long Id) {
        UserDTO userDTO = new UserDTO();
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
    @PutMapping(value = "/users/{Id}", name = "increased number of reports or is blocked")
    public ResponseEntity<UserDTO> updateUserAccount(@PathVariable Long Id/*UserDTO user*/) {
        return new ResponseEntity<>(new UserDTO(), HttpStatus.OK);
    }

    @DeleteMapping("/users/{Id}")
    public ResponseEntity<String> deleteUserAccount(@PathVariable Long Id) {
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    @GetMapping(value="/users/{Id}")
    public ResponseEntity<UserDTO> getUserRole(@PathVariable Long Id) {
        UserDTO userDTO = new UserDTO();
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }


}