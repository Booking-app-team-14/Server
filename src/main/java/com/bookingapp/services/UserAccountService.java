package com.bookingapp.services;

import com.bookingapp.entities.UserAccount;
import com.bookingapp.repositories.UserAccountIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAccountService {

    @Autowired
    private UserAccountIRepository userAccountRepository;

    //@Autowired
    //private JavaMailSender javaMailSender;



    /*public void registerUser(UserAccount user) {
        // Logika za čuvanje korisnika u bazi podataka
        //user.setActive(false); // Nalog nije aktivan dok se ne potvrdi putem emaila
        userAccountRepository.save(user);

        String link = generateLink(user);
        sendActivationEmail(user.getUsername(), link);
    }*/

    private String generateLink(UserAccount user) {
        // Implementirajte logiku za generisanje aktivacionog linka
        // Na primer, možete koristiti UUID
        return "http://your-app-url/aktivacija/" + user.getId();
    }

 /*   private void sendActivationEmail(String email, String link) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try {
            helper.setTo(email);
            helper.setSubject("Account activation");
            helper.setText("Please activate your account by clicking the following link: " + link, true);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            // Handle exception
            e.printStackTrace();
        }
    }*/

    public boolean activateAccount(String link) {
        // Logika za aktivaciju naloga
        // Implementirajte logiku za proveru linka i aktivaciju naloga
        return false;
    }

//    private final UserAccountIRepository userAccountRepository;
//
//    @Autowired
//    public UserAccountService(UserAccountIRepository userAccountRepository) {
//        this.userAccountRepository = userAccountRepository;
//    }
//
//    public UserAccount createUser() {
//        return null;
////        return userAccountRepository.save();
//    }
//
//    public UserAccount getUserById(Long userId) {
//
//        Optional<UserAccount> userOptional = userAccountRepository.findById(userId);
//        return userOptional.orElse(null);
//    }
//
//    public UserAccount updateUser(Long userId) {
//
//        Optional<UserAccount> userOptional = userAccountRepository.findById(userId);
//        if (userOptional.isPresent()) {
//            UserAccount existingUser = userOptional.get();
//            return userAccountRepository.save(existingUser);
//        }
//        return null;
//    }
//
//    public boolean deleteUser(Long userId) {
//
//        if (userAccountRepository.existsById(userId)) {
//            userAccountRepository.deleteById(userId);
//            return true;
//        }
//        return false;
//    }
//
//    public String getUserRole(Long userId) {
//
//        Optional<UserAccount> userOptional = userAccountRepository.findById(userId);
//        if (userOptional.isPresent()) {
//            UserAccount user = userOptional.get();
//
//            return user.getRole().toString();
//        }
//        return null;
//    }
//
//    public void save(UserAccount reportedUser) {
//
//    }
}
