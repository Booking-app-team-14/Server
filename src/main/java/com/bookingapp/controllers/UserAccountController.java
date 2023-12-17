package com.bookingapp.controllers;

import com.bookingapp.dtos.AdminDTO;
import com.bookingapp.dtos.GuestDTO;
import com.bookingapp.dtos.OwnerDTO;
import com.bookingapp.dtos.UserDTO;
import com.bookingapp.entities.*;
import com.bookingapp.enums.Role;
import com.bookingapp.services.ActivationService;
import com.bookingapp.services.UserAccountService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class UserAccountController {

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private ActivationService activationService;

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping(value = "/register/users", name = "register user") // api/users?type=GUEST
    public ResponseEntity<Long> registerUserAccount(@RequestBody UserDTO userDTO, @RequestParam("type") Role role) throws MessagingException, UnsupportedEncodingException {
        UserAccount user = switch (role) {
            case GUEST -> new Guest((GuestDTO) userDTO);
            case OWNER -> new Owner((OwnerDTO) userDTO);
            case ADMIN -> new Admin((AdminDTO) userDTO);
            default -> null;
        };
        if (user == null) {
            return new ResponseEntity<>((long) -1, HttpStatus.BAD_REQUEST);
        }
        //cuvanje korisnika
        user.setRole(role);
        userAccountService.save(user);

        // aktivacioni token
        Activation activation = new Activation();
        activation.setId(Math.toIntExact(user.getId()));
        activation.setUser(user);
        activation.setCreationDate(LocalDateTime.now());
        activation.setExpirationDate(LocalDateTime.now().plusHours(24));

        activationService.save(activation);

        //slanje mejla

        String subject = "Please verify your registration";
        String senderName = "BookingApp14";

        String mailContent = "<div style='text-align: center; font-family: Arial, sans-serif;'>";
        mailContent += "<h1 style='color: #007BFF;'>VERIFY YOUR ACCOUNT</h1>";
        mailContent += "<p>Dear " + user.getFirstName() + " " + user.getLastName() + ",</p>";
        mailContent += "<p>Please click the link below to verify your registration:</p>";
        mailContent += "<p><span style='color: #800080;'>Link expires in 24 hours</span></p>";

        mailContent += "<h3><a href=\"http://localhost:4200/verify?userId=" + user.getId() + "\">VERIFY</a></h3>";
        mailContent += "<p>Thank you,<br>BookingApp Team 14</p>";
        mailContent += "</div>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("bookingappteam448@gmail.com", senderName);
        helper.setTo(user.getUsername());
        helper.setSubject(subject);
        helper.setText(mailContent, true);

        mailSender.send(message);
        return new ResponseEntity<>(user.getId(), HttpStatus.CREATED);

    }


    @PutMapping("/verify/users/{userId}")
    public ResponseEntity<String> verifyUserAccount(@PathVariable Long userId) {
        try {
            Activation activation= activationService.getActivationByUserId(userId);

            // check if activation is expired
            if (activation.isExpired()) {
                return new ResponseEntity<>("Activation link has expired.", HttpStatus.BAD_REQUEST);
            }
            userAccountService.verifyUserAccount(userId);
            return new ResponseEntity<>("User successfully verified.", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
        }
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

    @GetMapping(value = "/users/byUsername/{username}")
    public ResponseEntity<?> getUserAccountByUsername(@PathVariable String username) {
        UserAccount user = userAccountService.findByUsername(username);

        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
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