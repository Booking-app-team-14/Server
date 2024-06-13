package com.bookingapp.controllers;

import com.bookingapp.dtos.JwtAuthenticationRequest;
import com.bookingapp.dtos.UserRequest;
import com.bookingapp.dtos.UserTokenState;
import com.bookingapp.entities.UserAccount;
import com.bookingapp.exceptions.ResourceConflictException;
import com.bookingapp.services.UserAccountService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import com.bookingapp.util.TokenUtils;

//Kontroler zaduzen za autentifikaciju korisnika
@RestController
@RequestMapping(value = "/api")
//@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {

    @Autowired
    private TokenUtils tokenUtils;


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserAccountService userService;


    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserAccount user = (UserAccount) authentication.getPrincipal();

        if (!user.isVerified()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not verified");
        }

        if (user.isBlocked()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Bad credentials!");
        }

        String jwt = tokenUtils.generateToken(user.getUsername());
        return ResponseEntity.ok(jwt);

    }

    @GetMapping("/role")
    public ResponseEntity<String> getRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount user = (UserAccount) authentication.getPrincipal();
        return ResponseEntity.ok(user.getRole().toString());
    }

}
