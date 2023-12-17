package com.bookingapp.repositories;

import com.bookingapp.entities.Activation;
import com.bookingapp.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ActivationRepository extends JpaRepository<Activation, Integer> {
    public Activation findOneById(Integer id);

    Optional<Activation> findByUser(UserAccount user);

    @Query("SELECT a FROM Activation a WHERE a.user.Id = :userId")
    Optional<Activation> findByUserId(@Param("userId") Long userId);
}
