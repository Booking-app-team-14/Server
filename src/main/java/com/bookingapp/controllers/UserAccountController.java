package com.bookingapp.controllers;

import com.bookingapp.entities.UserAccount;
import com.bookingapp.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserAccountController {

    private final UserAccountService userAccountService;
    @Autowired
    public UserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }


    @PostMapping("/{Id}}")
    public ResponseEntity<UserAccount> createUserAccount(@RequestBody UserAccount userAccount) {
        UserAccount createdUser = userAccountService.createUser(userAccount);
        return ResponseEntity.status(HttpStatus.OK).body(createdUser);
    }


    @GetMapping("/{Id}")
    public ResponseEntity<UserAccount> getUserAccountById(@PathVariable Long Id) {
        UserAccount userAccount = userAccountService.getUserById(Id);
        if (userAccount != null) {
            return ResponseEntity.ok(userAccount);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/{Id}")
    public ResponseEntity<UserAccount> updateUserAccount(@PathVariable Long Id, @RequestBody UserAccount updatedUser) {
        UserAccount userAccount = userAccountService.updateUser(Id, updatedUser);
        if (userAccount != null) {
            return ResponseEntity.ok(userAccount);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<Void> deleteUserAccount(@PathVariable Long Id) {
        boolean deleted = userAccountService.deleteUser(Id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping(value="/{Id}",consumes = "application/json")
    public ResponseEntity<String> getUserRole(@PathVariable Long Id) {
        String role = userAccountService.getUserRole(Id);
        if (role != null) {
            return ResponseEntity.ok(role);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}