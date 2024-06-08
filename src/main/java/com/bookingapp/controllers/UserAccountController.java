package com.bookingapp.controllers;

import com.bookingapp.dtos.*;
import com.bookingapp.entities.*;
import com.bookingapp.enums.NotificationType;
import com.bookingapp.enums.RequestStatus;
import com.bookingapp.enums.Role;
import com.bookingapp.repositories.ImagesRepository;
import com.bookingapp.services.AccommodationService;
import com.bookingapp.services.ActivationService;
import com.bookingapp.services.ReservationRequestService;
import com.bookingapp.services.UserAccountService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class UserAccountController {

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private AccommodationService accommodationService;

    @Autowired
    private ActivationService activationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ReservationRequestService reservationRequestService;

    @GetMapping(value = "/users/{id}/usernameAndNumberOfCancellations", produces = "text/plain")
    public ResponseEntity<String> getUsernameAndNumberOfCancellations(@PathVariable Long id) {
        UserAccount userAccount = userAccountService.getUserById(id);
        if (userAccount == null) {
            return new ResponseEntity<>("Account Not Found", HttpStatus.NOT_FOUND);
        }
        if (userAccount.getRole() == Role.GUEST) {
            Guest guest = (Guest) userAccount;
            String username = userAccount.getUsername();
            int numberOfCancellations = guest.getNumberOfCancellations();
            return new ResponseEntity<>(username + " | " + String.valueOf(numberOfCancellations), HttpStatus.OK);
        }
        return new ResponseEntity<>("Account Not Found", HttpStatus.NOT_FOUND);
    }

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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userAccountService.save(user);
        ImagesRepository imagesRepository = new ImagesRepository();
        try {
            imagesRepository.addImage("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",
                    "png", "userAvatars/user-" + user.getId() + ".png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        user.setProfilePicturePath("userAvatars/user-" + user.getId() + ".png");
        user.setNotWantedNotificationTypes(new HashSet<>());
        userAccountService.save(user);

        // aktivacioni token
        Activation activation = new Activation();
        activation.setId(Math.toIntExact(user.getId()));
        activation.setUser(user);
        activation.setCreationDate(LocalDateTime.now());
        activation.setExpirationDate(LocalDateTime.now().plusHours(24));

        activationService.save(activation);

        activationService.sendActivationEmail(user);

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

    @GetMapping(value = "/users/token/{token}", name = "get user id by bearer token")
    public ResponseEntity<Long> getUserAccountByToken(@PathVariable String token) {
        Long userId = userAccountService.getUserIdByToken(token);
        if (userId == null) {
            return new ResponseEntity<>((long) -1, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userId, HttpStatus.OK);
    }

    @GetMapping(value = "/users/androidToken/{token}", name = "android get user id by bearer token")
    public ResponseEntity<Long> getUserAccountByTokenAndroid(@PathVariable String token) {
        Long userId = userAccountService.getUserIdByToken(token);
        if (userId == null) {
            return new ResponseEntity<>((long) -1, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userId, HttpStatus.OK);
    }

    @PutMapping(value = "/users/{id}", name = "user updates his profile")
    public ResponseEntity<String> updateUserAccount(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        UserAccount user = userAccountService.getUserById(id);
        if (user == null) {
            return new ResponseEntity<>("Account Not Found", HttpStatus.NOT_FOUND);
        }
        if (userDTO.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setAddress(userDTO.getAddress());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        userAccountService.save(user);
        return new ResponseEntity<>("Account Updated", HttpStatus.OK);
    }

    @PutMapping(value = "/users/{id}/basicInfo", name = "user updates his basic info")
    public ResponseEntity<String> updateUserBasicInfo(@PathVariable Long id, @RequestBody UserBasicInfoNoImageDTO userDTO) {
        UserAccount user = userAccountService.getUserById(id);
        if (user == null) {
            return new ResponseEntity<>("Account Not Found", HttpStatus.NOT_FOUND);
        }
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setAddress(userDTO.getAddress());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        userAccountService.save(user);
        return new ResponseEntity<>("Account Updated", HttpStatus.OK);
    }

    @PutMapping(value = "/users/{id}/password", name = "user updates his password")
    public ResponseEntity<String> updateUserPassword(@PathVariable Long id, @RequestBody String password) {
        UserAccount user = userAccountService.getUserById(id);
        if (user == null) {
            return new ResponseEntity<>("Account Not Found", HttpStatus.NOT_FOUND);
        }
        user.setPassword(passwordEncoder.encode(password));
        userAccountService.save(user);
        return new ResponseEntity<>("Account Updated", HttpStatus.OK);
    }

    @PutMapping(value = "/guest/{id}", name = "guest cancels his reservations")
    public ResponseEntity<String> updateGuestAccount(@PathVariable Long id) {
        boolean ok = userAccountService.increaseNumberOfCancellations(id);
        if(!ok){
            return new ResponseEntity<>("Account Not Found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Account Updated", HttpStatus.OK);
    }

    @GetMapping(value = "/users/{Id}")
    public ResponseEntity<?> getUserAccountById(@PathVariable Long Id){
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

    @GetMapping(value = "/users/{id}/basicInfo")
    public ResponseEntity<UserBasicInfoDTO> getUserBasicInfoById(@PathVariable Long id) {
        UserAccount u = userAccountService.getUserById(id);
        if (u == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        UserBasicInfoDTO user = new UserBasicInfoDTO(u);
        user.setProfilePictureBytes(userAccountService.getUserImage(id));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/users/owner/{userId}")
    public ResponseEntity<?> getUserAccountInfoById(@PathVariable Long userId){
        UserAccount user = userAccountService.getUserById(userId);
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new UserInfoDTO(user), HttpStatus.OK);

    }

    @GetMapping(value = "/users/byUsername/{username}")
    public ResponseEntity<?> getUserAccountByUsername(@PathVariable String username) {
        UserAccount user = userAccountService.findByUsername(username);
        System.out.println(user);

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
        else if (user.getRole() == Role.ADMIN) {
            Admin admin = (Admin) user;
            return new ResponseEntity<>(new AdminDTO(admin), HttpStatus.OK);
        }

        return new ResponseEntity<>(new AdminDTO((Admin) user), HttpStatus.OK);
    }

    @PostMapping(value = "/users/{id}/image", consumes = "text/plain", name = "user uploads avatar image for his profile")
    public ResponseEntity<Long> uploadUserImage(@PathVariable Long id, @RequestBody String imageBytes) {
        boolean ok = userAccountService.uploadAvatarImage(id, imageBytes);
        if (!ok) {
            return new ResponseEntity<>((long) -1, HttpStatus.BAD_REQUEST);
        }
        UserAccount user = userAccountService.getUserById(id);
        ImagesRepository imagesRepository = new ImagesRepository();
        String imageType = null;
        try {
            imageType = imagesRepository.getImageType(imageBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        user.setProfilePicturePath("userAvatars/user-" + id + "." + imageType);
        userAccountService.save(user);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping(value = "/users/{Id}/image")
    public ResponseEntity<String> getUserImageBytes(@PathVariable Long Id) {
        String imageBytes = userAccountService.getUserImage(Id);
        if (imageBytes == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(imageBytes, HttpStatus.OK);
    }

    @GetMapping(value = "/users/{id}/image-type-username")
    public ResponseEntity<String> getUsernameAndImage(@PathVariable Long id) {
        String imageBytes = userAccountService.getUserImage(id);
        if (imageBytes == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        ImagesRepository imagesRepository = new ImagesRepository();
        String imageType = null;
        try {
            imageType = imagesRepository.getImageType(imageBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        UserAccount user = userAccountService.getUserById(id);
        String username = user.getUsername();
        return new ResponseEntity<>(username + " | " + imageType + " | " + imageBytes, HttpStatus.OK);
    }

    @GetMapping(value = "/owners/{id}/rating")
    public ResponseEntity<Double> getOwnerRating(@PathVariable Long id) {
        Double rating = userAccountService.getOwnerRating(id);
        if (rating == null) {
            return new ResponseEntity<>(-1D, HttpStatus.OK);
        }
        return new ResponseEntity<>(rating, HttpStatus.OK);
    }

    @GetMapping(value = "/owners/{id}/ratingString")
    public ResponseEntity<String> getOwnerRatingString(@PathVariable Long id) {
        Double rating = userAccountService.getOwnerRating(id);
        if (rating == null) {
            return new ResponseEntity<>("-1", HttpStatus.OK);
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String formattedRating = decimalFormat.format(rating);
        return new ResponseEntity<>(formattedRating, HttpStatus.OK);
    }

    @DeleteMapping(value = "/users/{Id}")
    public ResponseEntity<String> deleteUserAccount(@PathVariable Long Id) {
        UserAccount userAccount = userAccountService.getUserById(Id);
        if (userAccount == null) {
            return new ResponseEntity<>("Account Not Found", HttpStatus.NOT_FOUND);
        }
        if (userAccount.getRole() == Role.GUEST) {
            Guest guest = (Guest) userAccount;
            if (reservationRequestService.guestHasActiveRequests(guest.getId()))
                return new ResponseEntity<>("Guest has active requests", HttpStatus.OK);
        }
        else if (userAccount.getRole() == Role.OWNER) {
            Owner owner = (Owner) userAccount;
            if (reservationRequestService.ownerHasActiveRequests(owner))
                return new ResponseEntity<>("Owner has active requests", HttpStatus.OK);
        }

//        Activation a = activationService.getActivationByUserId(Id);
//        activationService.deleteActivation(a);

        if (userAccount.getRole() == Role.GUEST) {
            reservationRequestService.cancelAllReservationsForGuest(userAccount.getId());
        }
        else if (userAccount.getRole() == Role.OWNER) {
            reservationRequestService.cancelAllReservationsForOwner(userAccount.getUsername());
            accommodationService.setApprovedToFalseForAllOwnersApartments(userAccount.getId());
        }

        userAccount.getNotWantedNotificationTypes().add(NotificationType.RESERVATION_REQUEST_RESPONSE);

        userAccount.setBlocked(true);
        userAccountService.save(userAccount);

//        userAccountService.deleteUser(Id);
//        userAccountService.deleteUserImage(Id);

        return new ResponseEntity<>("Account Deleted", HttpStatus.OK);
    }

    @PutMapping(value = "/users/{id}/favorite-accommodations/{accommodationId}", name = "guest adds favorite accommodation")
    public ResponseEntity<String> addGuestFavoriteAccommodation(@PathVariable Long id, @PathVariable Long accommodationId) {
        Guest guest = (Guest) userAccountService.getUserById(id);
        if (guest == null) {
            return new ResponseEntity<>("Guest Not Found", HttpStatus.NOT_FOUND);
        }
        Set<Accommodation> existingFavorites = guest.getFavouriteAccommodations();

        if (existingFavorites.stream().noneMatch(a -> a.getId().equals(accommodationId))) {
            if (existingFavorites == null)
                existingFavorites = new HashSet<>();

            Accommodation accommodation = accommodationService.getAccommodationById(accommodationId).orElse(null);
            if (accommodation != null && !existingFavorites.contains(accommodation)) {
                existingFavorites.add(accommodation);
                guest.setFavouriteAccommodations(existingFavorites);
                userAccountService.save(guest);
                return new ResponseEntity<>("Favorite Accommodation Updated", HttpStatus.OK);

            } else {
                return new ResponseEntity<>("Accommodation not found or already in favorites", HttpStatus.BAD_REQUEST);
            }
        } else
            return new ResponseEntity<>("Accommodation already in favourites!", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/users/{id}/favorite-accommodations/{accommodationId}", name = "user removes favorite accommodation")
    public ResponseEntity<String> removeUserFavoriteAccommodation(@PathVariable Long id, @PathVariable Long accommodationId) {
        Guest guest = (Guest) userAccountService.getUserById(id);
        if (guest == null) {
            return new ResponseEntity<>("Guest Not Found", HttpStatus.NOT_FOUND);
        }

        Set<Accommodation> favoriteAccommodations = guest.getFavouriteAccommodations();
        Accommodation ac = accommodationService.getAccommodationById(accommodationId).get();
        if (!favoriteAccommodations.contains(ac)) {
            return new ResponseEntity<>("Accommodation not found in favorites", HttpStatus.NOT_FOUND);
        }

        favoriteAccommodations.remove(ac);
        guest.setFavouriteAccommodations(favoriteAccommodations);
        userAccountService.save(guest);

        return new ResponseEntity<>("Favorite Accommodation Removed", HttpStatus.OK);
    }

    @GetMapping(value = "/users/favorite/{userId}")
    public ResponseEntity<List<FavouriteAccommodationDTO>> getUserAccountByUsername(@PathVariable Long userId) {
        UserAccount user = userAccountService.getUserById(userId);

        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        List<FavouriteAccommodationDTO> favouriteAccommodations = new ArrayList<>();
        Guest guest = (Guest) user;
        if (!guest.getFavouriteAccommodations().isEmpty()) {
            for (Accommodation acc : guest.getFavouriteAccommodations())
                favouriteAccommodations.add(new FavouriteAccommodationDTO(acc, accommodationService));
        }else {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
        return new ResponseEntity<>(favouriteAccommodations, HttpStatus.OK);

    }

    @GetMapping(value = "/users/{userId}/not-wanted-notifications")
    public ResponseEntity<List<NotificationType>> getNotWantedNotifications(@PathVariable Long userId) {
        UserAccount user = userAccountService.getUserById(userId);
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ArrayList<>(user.getNotWantedNotificationTypes()), HttpStatus.OK);
    }

    @PutMapping(value = "/users/{userId}/not-wanted-notifications")
    public ResponseEntity<Boolean> setNotWantedNotifications(@PathVariable Long userId, @RequestBody String notWantedNotificationType) {
        UserAccount user = userAccountService.getUserById(userId);
        if (user == null) {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }

        NotificationType type = NotificationType.valueOf(notWantedNotificationType);

        boolean response = user.getNotWantedNotificationTypes().contains(type) ? user.getNotWantedNotificationTypes().remove(type) : user.getNotWantedNotificationTypes().add(type);
        userAccountService.save(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}