package com.bookingapp.repositories;

import com.bookingapp.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountIRepository extends JpaRepository<UserAccount, Long> {

//    UserAccount save();
}
