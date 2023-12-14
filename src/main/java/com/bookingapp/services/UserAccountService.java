package com.bookingapp.services;

import com.bookingapp.dtos.UserRequest;
import com.bookingapp.entities.UserAccount;
import com.bookingapp.enums.Role;
import com.bookingapp.repositories.ImagesRepository;
import com.bookingapp.repositories.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
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
        account.setPassword(passwordEncoder.encode(account.getPassword()));
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount user = userAccountRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return user;
        }
    }
}
