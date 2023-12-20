package com.bookingapp.services;

import com.bookingapp.dtos.UserRequest;
import com.bookingapp.entities.UserAccount;
import com.bookingapp.repositories.ImagesRepository;
import com.bookingapp.repositories.UserAccountRepository;
import com.bookingapp.util.TokenUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserAccountService implements UserDetailsService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    ImagesRepository imagesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private JavaMailSender mailSender;

    public UserAccount getUserById(Long userId) {
        Optional<UserAccount> userOptional = userAccountRepository.findById(userId);
        return userOptional.orElse(null);
    }

    public UserAccount findById(Long id) throws AccessDeniedException {
        return userAccountRepository.findById(id).orElseGet(null);
    }

    /*public UserAccount findByUsername(String username) {
        Optional<UserAccount> userOptional = userAccountRepository.findByUsername(username);
        return userOptional.orElse(null);
    }*/
    //@Override
    public UserAccount findByUsername(String username) throws UsernameNotFoundException {
        return userAccountRepository.findByUsername(username);
    }

    public boolean deleteUser(Long userId) {
        if (userAccountRepository.existsById(userId)) {
            userAccountRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public List<UserAccount> findAll() throws AccessDeniedException {
        return userAccountRepository.findAll();
    }

    public String getUserRole(Long userId) {
        Optional<UserAccount> userOptional = userAccountRepository.findById(userId);
        if (userOptional.isPresent()) {
            UserAccount user = userOptional.get();
            return user.getRole().toString();
        }
        return null;
    }

    public void save(UserAccount account) {
        userAccountRepository.save(account);
    }

    public UserAccount save(UserRequest userRequest) {
        UserAccount u = new UserAccount();
        u.setUsername(userRequest.getUsername());

        // pre nego sto postavimo lozinku u atribut hesiramo je kako bi se u bazi nalazila hesirana lozinka
        // treba voditi racuna da se koristi isi password encoder bean koji je postavljen u AUthenticationManager-u kako bi koristili isti algoritam
        u.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        u.setFirstName(userRequest.getFirstname());
        u.setLastName(userRequest.getLastname());


        return this.userAccountRepository.save(u);
    }

    public boolean uploadAvatarImage(Long id, String imageBytes) {
        String imageType = null;
        try {
            imageType = imagesRepository.getImageType(imageBytes);
        } catch (IOException e) {
            return false;
        }

        deleteUserImage(id);

        String relativePath = String.format("userAvatars\\user-%d", id);
        relativePath += "." + imageType;
        try {
            imagesRepository.addImage(imageBytes, imageType, relativePath);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean deleteUserImage(Long id) {
        String relativePath = findUserImageName(id);
        if (relativePath == null) {
            return false;
        }
        return imagesRepository.deleteImage(relativePath);
    }

    public String getUserImage(Long id) {
        String relativePath = findUserImageName(id);
        if (relativePath == null) {
            return null;
        }
        try {
            return imagesRepository.getImageBytes(relativePath);
        } catch (IOException e) {
            return null;
        }
    }

    private String findUserImageName(Long id) {
        File directory = new File("src\\main\\resources\\images\\userAvatars");
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                String filename = file.getName();
                if (filename.startsWith("user-" + id)) {
                    return "userAvatars\\" + file.getName();
                }
            }
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount user = userAccountRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return user;
        }
    }


/*
    public void sendMail(Integer id) throws MessagingException, UnsupportedEncodingException {
        String subject = "Please verify your registration";
        String senderName = "BookingApp14";

        String mailContent = "<p>Dear, user </p>";
        mailContent +="<p>Please click the link below to verify your registration:</p>";
        mailContent +="<h3><a href=\"" + "http://localhost:4200/login" + id + "\">VERIFY</a></h3>";
        mailContent +="<p>Thank you<br>BookingApp Team 14</p>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("bookingappteam448@gmail.com", senderName);
        //helper.setTo("zivanovicmarija895@gmail.com");
        //helper.setTo(userAccountRepository.findByUsername());
        helper.setSubject(subject);
        helper.setText(mailContent, true);

        mailSender.send(message);
    }*/


    public void verifyUserAccount(Long userId) {
        UserAccount user = userAccountRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        user.setVerified(true);
        userAccountRepository.save(user);
    }

    public Long getUserIdByToken(String token) {
        String username = tokenUtils.getUsernameFromToken(token);
        if (username == null) {
            return null;
        }
        UserAccount user = userAccountRepository.findByUsername(username);
        if (user == null) {
            return null;
        }
        return user.getId();
    }

    public Iterable<Object> getOwners() {
        return userAccountRepository.findAllOwners();
    }
}
