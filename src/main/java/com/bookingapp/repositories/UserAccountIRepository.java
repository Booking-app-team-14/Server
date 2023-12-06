package com.bookingapp.repositories;

import com.bookingapp.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountIRepository extends JpaRepository<UserAccount, Long> {

//    UserAccount save();

    Optional<UserAccount> findByUsername(String username);
}
