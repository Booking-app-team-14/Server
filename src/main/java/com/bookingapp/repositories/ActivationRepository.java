package com.bookingapp.repositories;

import com.bookingapp.entities.Activation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivationRepository extends JpaRepository<Activation, Integer> {
    public Activation findOneById(Integer id);
}
