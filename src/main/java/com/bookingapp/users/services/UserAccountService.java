package com.bookingapp.users.services;

import com.bookingapp.users.models.UserAccount;
import com.bookingapp.users.repositories.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;

    @Autowired
    public UserAccountService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    public UserAccount createUser(UserAccount userAccount) {

        return userAccountRepository.save(userAccount);
    }

    public UserAccount getUserById(Long userId) {

        Optional<UserAccount> userOptional = userAccountRepository.findById(userId);
        return userOptional.orElse(null);
    }

    public UserAccount updateUser(Long userId, UserAccount updatedUser) {

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
}
