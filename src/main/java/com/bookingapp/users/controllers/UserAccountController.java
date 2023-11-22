package com.bookingapp.users.controllers;

import com.bookingapp.users.models.UserAccount;
import com.bookingapp.users.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserAccountController {

    private final UserAccountService userAccountService;
    @Autowired
    public UserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }


    @PostMapping("/create")
    public ResponseEntity<UserAccount> createUserAccount(@RequestBody UserAccount userAccount) {
        UserAccount createdUser = userAccountService.createUser(userAccount);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<UserAccount> getUserAccountById(@PathVariable Long userId) {
        UserAccount userAccount = userAccountService.getUserById(userId);
        if (userAccount != null) {
            return ResponseEntity.ok(userAccount);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/{userId}/update")
    public ResponseEntity<UserAccount> updateUserAccount(@PathVariable Long userId, @RequestBody UserAccount updatedUser) {
        UserAccount userAccount = userAccountService.updateUser(userId, updatedUser);
        if (userAccount != null) {
            return ResponseEntity.ok(userAccount);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<Void> deleteUserAccount(@PathVariable Long userId) {
        boolean deleted = userAccountService.deleteUser(userId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/{userId}/role")
    public ResponseEntity<String> getUserRole(@PathVariable Long userId) {
        String role = userAccountService.getUserRole(userId);
        if (role != null) {
            return ResponseEntity.ok(role);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}