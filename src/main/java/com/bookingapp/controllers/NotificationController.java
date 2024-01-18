package com.bookingapp.controllers;

import com.bookingapp.dtos.NotificationDTO;
import com.bookingapp.services.NotificationService;
import com.bookingapp.services.ReservationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    @GetMapping(value = "/{userId}", name = "user gets all his wanted notifications")
    public List<NotificationDTO> getAllWantedNotificationsForUser(@PathVariable Long userId) {
        return notificationService.getAllWantedNotificationsForUser(userId, reservationRequestService);
    }

}
