package com.bookingapp.services;

import com.bookingapp.entities.UserAccount;
import com.bookingapp.repositories.ImagesRepository;
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

    @Autowired
    ImagesRepository imagesRepository;

    public UserAccount getUserById(Long userId) {
        Optional<UserAccount> userOptional = userAccountRepository.findById(userId);
        return userOptional.orElse(null);
    }

    public boolean deleteUser(Long userId) {
        if (userAccountRepository.existsById(userId)) {
            userAccountRepository.deleteById(userId);
            return true;
        }
        return false;
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

    public boolean uploadAvatarImage(Long id, byte[] imageBytes) {
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

    public byte[] getUserImage(Long id) {
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

}
