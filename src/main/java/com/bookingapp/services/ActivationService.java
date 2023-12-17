package com.bookingapp.services;

import com.bookingapp.entities.Activation;
import com.bookingapp.repositories.ActivationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivationService {

    @Autowired
    private ActivationRepository activationRepository;

    public Activation save(Activation activation){return activationRepository.save(activation);}

    public Activation findOne(Integer id){return activationRepository.findById(id).orElseGet(null);}

    public Activation getActivationByUserId(Long userId) {

        return activationRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Activation not found for User with ID: " + userId));
    }
}
