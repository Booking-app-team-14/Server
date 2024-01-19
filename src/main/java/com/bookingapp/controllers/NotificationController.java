package com.bookingapp.controllers;

import com.bookingapp.dtos.NotificationDTO;
import com.bookingapp.services.AccommodationReviewService;
import com.bookingapp.services.NotificationService;
import com.bookingapp.services.ReservationRequestService;
import com.bookingapp.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ReservationRequestService reservationRequestService;

    @Autowired
    private AccommodationReviewService accommodationReviewService;

    @Autowired
    private ReviewService reviewService;

    @GetMapping(value = "/{userId}/{setSeen}", name = "user gets all his wanted notifications")
    public ResponseEntity<List<NotificationDTO>> getAllWantedNotificationsForUser(@PathVariable Long userId, @PathVariable Boolean setSeen) {
        List<NotificationDTO> notificationDTOS = notificationService.getAllWantedNotificationsForUser(userId, setSeen, reservationRequestService, accommodationReviewService, reviewService);
        return new ResponseEntity<>(notificationDTOS, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{notificationId}", name = "user deletes notification")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long notificationId) {
        notificationService.deleteNotification(notificationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
