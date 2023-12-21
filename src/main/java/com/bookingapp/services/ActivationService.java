package com.bookingapp.services;

import com.bookingapp.entities.Activation;
import com.bookingapp.entities.UserAccount;
import com.bookingapp.repositories.ActivationRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class ActivationService {

    @Autowired
    private ActivationRepository activationRepository;

    @Autowired
    private JavaMailSender mailSender;

    public Activation save(Activation activation){return activationRepository.save(activation);}

    public Activation findOne(Integer id){return activationRepository.findById(id).orElseGet(null);}

    public Activation getActivationByUserId(Long userId) {

        return activationRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Activation not found for User with ID: " + userId));
    }

    public void deleteActivation(Activation a) {
        activationRepository.delete(a);
    }

    public void sendActivationEmail(UserAccount user) throws MessagingException, UnsupportedEncodingException {
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
    }

}
