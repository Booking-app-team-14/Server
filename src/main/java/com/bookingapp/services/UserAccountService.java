package com.bookingapp.services;

import com.bookingapp.entities.UserAccount;
import com.bookingapp.repositories.UserAccountIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAccountService {

    private final UserAccountIRepository userAccountRepository;

    @Autowired
    public UserAccountService(UserAccountIRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    public UserAccount createUser() {

        return userAccountRepository.save();
    }

    public UserAccount getUserById(Long userId) {

        Optional<UserAccount> userOptional = userAccountRepository.findById(userId);
        return userOptional.orElse(null);
    }

    public UserAccount updateUser(Long userId) {

        Optional<UserAccount> userOptional = userAccountRepository.findById(userId);
        if (userOptional.isPresent()) {
            UserAccount existingUser = userOptional.get();
            return userAccountRepository.save(existingUser);
        }
        return null;
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

    public void save(UserAccount reportedUser) {

    }
}
