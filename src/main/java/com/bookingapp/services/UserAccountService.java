package com.bookingapp.services;

import com.bookingapp.entities.UserAccount;
import com.bookingapp.repositories.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
public class UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    public UserAccount getUserById(Long userId) {
        Optional<UserAccount> userOptional = userAccountRepository.findById(userId);
        return userOptional.orElse(null);
    }

//    public boolean deleteUser(Long userId) {
//        if (userAccountRepository.existsById(userId)) {
//            userAccountRepository.deleteById(userId);
//            return true;
//        }
//        return false;
//    }

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

    public boolean uploadAvatarImage(Long id, byte[] imageBytes) {
        ByteArrayInputStream inStream = new ByteArrayInputStream(imageBytes);
        BufferedImage newImage = null;
        String imageType = null;
        try {
            ImageInputStream iis = ImageIO.createImageInputStream(new ByteArrayInputStream(imageBytes));
            ImageReader reader = ImageIO.getImageReaders(iis).next();
            reader.setInput(iis, true);
            imageType = reader.getFormatName();
        } catch (IOException e) {
            return false;
        }
        String path = String.format("src\\main\\resources\\images\\userAvatars\\user-%d", id);
        path += "." + imageType;
        try {
            newImage = ImageIO.read(inStream);
            ImageIO.write(newImage, "png", new File(path));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
