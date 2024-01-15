package com.bookingapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/send/notification")
    public void sendNotification(@Payload String message) {
        // TODO: sacuvaj u bazi notifikaciju sve normalno i samo preko web socketa posalji poruku da se taj i taj tip notifikacije poslao useru
        simpMessagingTemplate.convertAndSend("/socket-publisher", "notification_type_sent");
    }
}