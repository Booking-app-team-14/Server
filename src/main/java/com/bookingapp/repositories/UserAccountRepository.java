package com.bookingapp.repositories;

import com.bookingapp.entities.Guest;
import com.bookingapp.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    //Optional<UserAccount> findByUsername(String username);
    UserAccount findByUsername(String username);

    @Query("SELECT u FROM UserAccount u WHERE u.role = 'OWNER'")
    Iterable<Object> findAllOwners();

    @Query("SELECT u FROM UserAccount u WHERE u.role = 'GUEST'")
    List<Guest> findAllGuests();
}
