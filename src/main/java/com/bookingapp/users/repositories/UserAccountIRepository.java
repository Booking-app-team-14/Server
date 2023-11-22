package com.bookingapp.users.repositories;

import com.bookingapp.users.models.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountIRepository extends JpaRepository<UserAccount, Long> {

}
