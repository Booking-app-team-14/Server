package com.bookingapp.controllers;

import com.bookingapp.dtos.NotificationDTO;
import com.bookingapp.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class NotificationController {

    @Autowired
    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }


    @PostMapping(value="/notifications/{Id}",name="guest makes a new request or review")
    public ResponseEntity<NotificationDTO> createNotification(/*@RequestBody NotificationDTO notification*/) {
        return new ResponseEntity<>(new NotificationDTO(), HttpStatus.CREATED);
    }

    @GetMapping(value ="/notifications", name = "gets all the notification for the user by the id")
    public ResponseEntity<List<NotificationDTO>> getNotificationsByUserId(@PathVariable Long userId) {
        List<NotificationDTO> notificationsDTO = new ArrayList<>();
        return new ResponseEntity<>(notificationsDTO, HttpStatus.OK);

    }
    @PutMapping(value = "/notifications/{Id}" ,/*consumes = "text/plain",*/ name = "updates the time when the notification has been sent")
    public ResponseEntity<NotificationDTO> updateNotification(@PathVariable Long Id /*@RequestBody NotificationDTO updatedNotification*/){

        return new ResponseEntity<>(new NotificationDTO(), HttpStatus.OK);
    }
    @DeleteMapping("/notifications/{Id}")
    public ResponseEntity<String> deleteNotification(@PathVariable Long Id) {
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }


}
